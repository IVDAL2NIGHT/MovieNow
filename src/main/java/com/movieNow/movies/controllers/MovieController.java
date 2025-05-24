package com.movieNow.movies.controllers;

import com.movieNow.movies.models.MovieRatingDTO;
import com.movieNow.movies.models.Rate;
import com.movieNow.movies.models.Review;
import com.movieNow.movies.services.MovieService;
import com.movieNow.movies.models.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * La clase MovieController proporciona endpoints REST para gestionar películas.
 * Permite obtener todas las películas y recuperar los detalles de una película específica basada en su ID de IMDb.
 */
@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public ResponseEntity<List<Movie>> allMovies() {
        return new ResponseEntity<List<Movie>>(movieService.allMovies(), HttpStatus.OK);
    }

    @GetMapping("/{imdbId}")
    public ResponseEntity<java.util.Optional<Movie>> gettingSingleMovie(@PathVariable String imdbId) {
        return new ResponseEntity<Optional<Movie>>(movieService.singleMovie(imdbId),HttpStatus.OK);
    }

    @GetMapping("/{imdbId}/reviews")
    public ResponseEntity<List<Review>> getMovieReviews(@PathVariable String imdbId) {
        return new ResponseEntity<>(movieService.getAllMovieReviews(imdbId), HttpStatus.OK);
    }

    @GetMapping("/{imdbId}/rates")
    public ResponseEntity<Map<String, Object>> getMovieRate(@PathVariable String imdbId) {
        Movie movie = movieService.singleMovie(imdbId)
                .orElseThrow(() -> new RuntimeException("Película no encontrada"));

        Map<String, Object> response = new HashMap<>();
        response.put("rates", movie.getRates());
        response.put("averageRating", movie.getAverageRating());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }









}