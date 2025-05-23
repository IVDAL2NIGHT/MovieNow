package com.movieNow.movies.services;

import com.movieNow.movies.models.Rate;
import com.movieNow.movies.models.Review;
import com.movieNow.movies.models.User;
import com.movieNow.movies.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RateService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Rate createrate(String imdbId, String username, int rating) {
        // Validar que el rating esté entre 1 y 5
        if (rating < 1 || rating > 5) {    // Cambié 'rate' por 'rating'
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 5 estrellas");
        }

        // Buscar usuario y verificar que existe
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Si el usuario no tiene roles asignados, inicializar la lista
        if (user.getRole() == null) {
            user.setRole(new ArrayList<>());
            userRepository.save(user);
        }

        // Crear y configurar la calificación
        Rate rateObj = new Rate(imdbId, "", username, rating);  // Agregué un string vacío como título temporal
        rateObj.setUser(user);

        return mongoTemplate.save(rateObj);
    }
}