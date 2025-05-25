package com.movieNow.movies.services;

import com.movieNow.movies.models.Rate;
import com.movieNow.movies.models.Movie;
import com.movieNow.movies.models.User;
import com.movieNow.movies.repository.MovieRepository;
import com.movieNow.movies.repository.RateRepository;
import com.movieNow.movies.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.Optional;

@Service

public class RateService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Rate createrate(String imdbId, String username, int rating) {
        // Validar el rating
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 5 estrellas");
        }

        // Buscar película
        Movie movie = movieRepository.findByImdbId(imdbId)
                .orElseThrow(() -> new RuntimeException("Película no encontrada"));

        // Buscar usuario
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Inicializar roles de usuario si es necesario
        if (user.getRole() == null) {
            user.setRole(new ArrayList<>());
            userRepository.save(user);
        }

        // Verificar calificación existente
        Rate existingRate = mongoTemplate.findOne(
                new Query(Criteria.where("imdbId").is(imdbId).and("username").is(username)),
                Rate.class
        );

        if (existingRate != null) {
            // Actualizar calificación existente
            existingRate.setRate(rating);
            Rate updatedRate = mongoTemplate.save(existingRate);

            // Actualizar la lista de rates en la película
            updateMovieRates(movie, updatedRate, true);

            return updatedRate;
        }

        // Crear nueva calificación
        Rate newRate = new Rate(imdbId, movie.getTitle(), username, rating);
        newRate.setUser(user);
        Rate savedRate = mongoTemplate.save(newRate);

        // Actualizar la película con el nuevo rate
        updateMovieRates(movie, savedRate, false);

        return savedRate;
    }

    private void updateMovieRates(Movie movie, Rate rate, boolean isUpdate) {
        // Inicializar la lista de rates si es necesario
        if (movie.getRates() == null) {
            movie.setRates(new ArrayList<>());
        }

        if (isUpdate) {
            // Actualizar rate existente
            movie.getRates().removeIf(r -> r.getUsername().equals(rate.getUsername()));
        }

        // Añadir el rate
        movie.getRates().add(rate);

        // Calcular y actualizar el promedio
        double average = movie.getRates().stream()
                .mapToInt(Rate::getRate)
                .average()
                .orElse(0.0);

        // Redondear a un decimal
        double roundedAverage = Math.round(average * 10.0) / 10.0;

        // Actualizar el promedio en la película
        movie.setAverageRating(roundedAverage);

        // Guardar la película actualizada
        movieRepository.save(movie);
    }
}

