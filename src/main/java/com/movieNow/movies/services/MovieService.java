package com.movieNow.movies.services;

import com.movieNow.movies.models.Movie;
import com.movieNow.movies.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Clase de servicio para manejar operaciones relacionadas con películas en la aplicación.
 * Esta clase interactúa con el MovieRepository para recuperar datos de la base de datos.
 * Proporciona métodos para obtener todas las películas y recuperar una película específica por su identificador IMDb.

 * Responsabilidades:
 * - Obtener todos los registros de películas de la base de datos.
 * - Obtener detalles de una película específica por su identificador IMDb.

 * Anotaciones:
 * - @Service: Marca esta clase como un servicio de Spring para la inyección de dependencias.
 */

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> allMovies(){
        return movieRepository.findAll();
    }

    public Optional<Movie> singleMovie(String imdbId){
        return movieRepository.findByImdbId(imdbId);
    }
}

