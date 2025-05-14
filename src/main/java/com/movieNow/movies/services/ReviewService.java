package com.movieNow.movies.services;

import com.movieNow.movies.models.Movie;
import com.movieNow.movies.models.Review;
import com.movieNow.movies.models.User;
import com.movieNow.movies.repository.ReviewRepository;
import com.movieNow.movies.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Review createReview(String reviewBody, String imdbId, String username){

        // Primero buscamos al usuario
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Creamos la reseña con la referencia al usuario
        Review review  = new Review(reviewBody,username);
        review.setUser(user);

        // Guardamos la reseña
        reviewRepository.insert(review);

        // Actualizamos la película con la referencia a la reseña
        mongoTemplate.update(Movie.class)
                .matching(Criteria.where("imdbId").is(imdbId))
                .apply(new Update().push("reviewsId").value(review))
                .first();
        return review;
    }

}
