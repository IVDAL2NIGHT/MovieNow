package com.movieNow.movies.services;

import com.movieNow.movies.models.Movie;
import com.movieNow.movies.models.MovieList;
import com.movieNow.movies.models.User;
import com.movieNow.movies.repository.MovieListRepository;
import com.movieNow.movies.repository.MovieRepository;
import com.movieNow.movies.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MovieListService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MovieListRepository movieListRepository;

    @Autowired
    private MovieRepository movieRepository;

    public MovieList createMovieList(String username, String title, String description) {
        // Buscar usuario y verificar que existe
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Si el usuario no tiene roles asignados, inicializar la lista
        if (user.getRole() == null) {
            user.setRole(new ArrayList<>());
            userRepository.save(user);
        }

        MovieList movieList = new MovieList(username, title, description);
        movieList.setUser(user);

        // Guardar la lista de películas en la colección de movieLists
        movieList = mongoTemplate.save(movieList);

        // Actualizar el usuario añadiendo la nueva lista a su array de listas
        Update update = new Update().push("movieLists", movieList);
        mongoTemplate.updateFirst(
                Query.query(Criteria.where("_id").is(user.getId())), // Usando el ID del usuario
                update,
                User.class
        );

        return movieList;
    }

    public void removeMovieList(String listId) {
        // Buscar la lista y verificar que existe
        MovieList movieList = movieListRepository.findById(new ObjectId(listId))
                .orElseThrow(() -> new RuntimeException("Lista no encontrada"));

        // Buscar el usuario asociado
        User user = userRepository.findByUsername(movieList.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Eliminar la lista de películas de la colección
        movieListRepository.deleteById(new ObjectId(listId));

        // Actualizar el usuario removiendo la referencia a la lista
        Update update = new Update().pull("movieLists", Query.query(Criteria.where("_id").is(listId)));
        mongoTemplate.updateFirst(
                Query.query(Criteria.where("_id").is(user.getId())),
                update,
                User.class
        );
    }

    public MovieList addMovieToList(String listId, String imdbId) {
        // Buscar la lista y verificar que existe
        MovieList movieList = movieListRepository.findById(new ObjectId(listId))
                .orElseThrow(() -> new RuntimeException("Lista no encontrada"));

        // Buscar la película y verificar que existe
        Movie movie = movieRepository.findByImdbId(imdbId)
                .orElseThrow(() -> new RuntimeException("Película no encontrada"));

        // Buscar el usuario asociado a la lista
        User user = userRepository.findByUsername(movieList.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verificar si la película ya está en la lista
        if (!movieList.getMovies().contains(movie)) {
            movieList.getMovies().add(movie);
            movieList = movieListRepository.save(movieList);

            // Actualizar la lista de películas en el usuario
            Query query = Query.query(Criteria.where("_id").is(user.getId())
                    .and("movieLists._id").is(new ObjectId(listId)));
        
            Update update = new Update().set("movieLists.$.movies", movieList.getMovies());
        
            mongoTemplate.updateFirst(query, update, User.class);
        }

        return movieList;
    }

    public MovieList removeMovieFromList(String listId, String imdbId) {
        // Buscar la lista y verificar que existe
        MovieList movieList = movieListRepository.findById(new ObjectId(listId))
                .orElseThrow(() -> new RuntimeException("Lista no encontrada"));

        // Buscar la película y verificar que existe
        Movie movie = movieRepository.findByImdbId(imdbId)
                .orElseThrow(() -> new RuntimeException("Película no encontrada"));

        // Buscar el usuario asociado a la lista
        User user = userRepository.findByUsername(movieList.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Remover la película de la lista si existe
        if (movieList.getMovies().removeIf(m -> m.getImdbId().equals(imdbId))) {
            movieList = movieListRepository.save(movieList);

            // Actualizar la lista de películas en el usuario
            Query query = Query.query(Criteria.where("_id").is(user.getId())
                    .and("movieLists._id").is(new ObjectId(listId)));
        
            Update update = new Update().set("movieLists.$.movies", movieList.getMovies());
        
            mongoTemplate.updateFirst(query, update, User.class);
        }

        return movieList;
    }

}