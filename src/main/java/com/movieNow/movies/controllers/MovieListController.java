package com.movieNow.movies.controllers;

import com.movieNow.movies.models.MovieList;
import com.movieNow.movies.services.MovieListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/movieList")
public class MovieListController {

    @Autowired
    private MovieListService movieListService;

    @PostMapping
    public ResponseEntity<MovieList> createMovieList(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        String title = payload.get("title");
        String description = payload.get("description");

        // Validación básica
        if (username == null || title == null || description == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(
                movieListService.createMovieList(username, title, description),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{listId}")
    public ResponseEntity<Void> deleteMovieList(@PathVariable String listId) {
        try {
            movieListService.removeMovieList(listId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{listId}/movies")
    public ResponseEntity<MovieList> addMovieToList(
            @PathVariable String listId,
            @RequestBody Map<String, String> payload) {

        String imdbId = payload.get("imdbId");

        // Validación básica
        if (imdbId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            return new ResponseEntity<>(
                    movieListService.addMovieToList(listId, imdbId),
                    HttpStatus.OK
            );
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{listId}/movies/{imdbId}")
    public ResponseEntity<MovieList> removeMovieFromList(
            @PathVariable String listId,
            @PathVariable String imdbId) {

        try {
            return new ResponseEntity<>(
                    movieListService.removeMovieFromList(listId, imdbId),
                    HttpStatus.OK
            );
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}