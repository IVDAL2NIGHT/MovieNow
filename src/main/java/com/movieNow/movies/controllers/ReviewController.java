package com.movieNow.movies.controllers;

import com.movieNow.movies.services.ReviewService;
import com.movieNow.movies.models.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * La clase ReviewController proporciona endpoints REST para gestionar reseñas
 * asociadas con películas en la aplicación. Permite crear nuevas reseñas
 * de películas a través de una petición POST.
 *
 * Endpoints:
 * - POST /api/v1/reviews: Crea una nueva reseña para una película, asociándola
 * con un usuario específico y una película basada en los datos de entrada. Los detalles
 * de la reseña se persisten en la base de datos y se asocian con la película objetivo.
 *
 * Dependencias:
 * - ReviewService: Maneja la lógica de negocio para crear y gestionar reseñas.
 *
 * Anotaciones:
 * - @RestController: Marca esta clase como un controlador para manejar peticiones HTTP.
 * - @RequestMapping("/api/v1/reviews"): Define la ruta URL base para los
 * endpoints del controlador.
 *
 * Métodos:
 * - createReview: Maneja peticiones POST para crear una nueva reseña. Toma los detalles
 * de la reseña del cuerpo de la petición, procesa los datos a través del ReviewService,
 * y devuelve la reseña creada envuelta en un ResponseEntity con
 * estado HTTP 201 (Created).
 */

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Map<String, String> payload) {
        return new ResponseEntity<Review>(
                reviewService.createReview(
                        payload.get("reviewBody"),
                        payload.get("imdbId"),
                        payload.get("username")
                ),
                HttpStatus.CREATED);
    }
}

