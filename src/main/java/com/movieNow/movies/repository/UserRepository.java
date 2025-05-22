package com.movieNow.movies.repository;

import com.movieNow.movies.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Interfaz de repositorio para gestionar entidades User almacenadas en una base de datos MongoDB.
 * Extiende MongoRepository de Spring Data para aprovechar sus métodos CRUD integrados.

 * Responsabilidades:
 * - Maneja operaciones de base de datos relacionadas con la entidad User.

 * Métodos Principales:
 * - findByUsername: Recupera una entidad User basada en el nombre de usuario proporcionado.

 * Anotaciones:
 * - @Repository: Indica que esta interfaz es un repositorio de Spring,
 *   habilitando la inyección de dependencias y el mecanismo de traducción de excepciones.

 * Información de la Entidad:
 * - La entidad User representa un objeto usuario almacenado en la colección "user" en MongoDB.
 */

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
}