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