package com.movieNow.movies.services;

import com.movieNow.movies.models.Movie;
import com.movieNow.movies.models.Review;
import com.movieNow.movies.models.User;
import com.movieNow.movies.repository.ReviewRepository;
import com.movieNow.movies.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

/**
 * La clase ReviewService proporciona la lógica de negocio para manejar operaciones relacionadas con reseñas.
 * Este servicio interactúa con ReviewRepository, UserRepository y MongoTemplate
 * para realizar operaciones como crear reseñas y vincularlas a películas y usuarios.
 *
 * Responsabilidades:
 * - Crear y persistir reseñas para películas.
 * - Manejar la validación de usuarios y asociar reseñas con usuarios existentes.
 * - Actualizar registros de películas para incluir las reseñas asociadas.
 *
 * Dependencias:
 * - ReviewRepository: Maneja operaciones CRUD para reseñas almacenadas en la base de datos.
 * - UserRepository: Valida usuarios y recupera información de usuario.
 * - MongoTemplate: Facilita operaciones complejas de MongoDB como actualizar documentos con reseñas incrustadas.
 *
 * Métodos:
 * - createReview(String reviewBody, String imdbId, String username):
 * Crea una reseña, la asocia con un usuario y una película, y actualiza el
 * documento de película relacionado en la base de datos.
 *
 * Excepciones:
 * - Lanza RuntimeException si el usuario no se encuentra.
 * - Lanza RuntimeException si la película no se encuentra.
 */

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Review createReview(String reviewBody, String imdbId, String username) {
        // Buscar usuario y verificar que existe
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Si el usuario no tiene roles asignados, inicializar la lista
        if (user.getRole() == null) {
            user.setRole(new ArrayList<>());
            userRepository.save(user);
        }

        // Crear y configurar la reseña con toda la información del usuario
        Review review = new Review(reviewBody, username);
        review.setUser(user); // Esto guardará el usuario completo

        // Guardar la reseña en la colección de reviews
        review = reviewRepository.save(review);

        // Buscar la película
        Movie movie = Optional.ofNullable(mongoTemplate.findOne(
                Query.query(Criteria.where("imdbId").is(imdbId)),
                Movie.class
        )).orElseThrow(() -> new RuntimeException("Película no encontrada"));

        // Usar MongoTemplate para actualizar la película con la review completa
        Update update = new Update().push("reviews", review);
        mongoTemplate.updateFirst(
                Query.query(Criteria.where("imdbId").is(imdbId)),
                update,
                Movie.class
        );

        return review;
    }
}