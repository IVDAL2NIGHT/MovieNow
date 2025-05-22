package com.movieNow.movies.repository;

import com.movieNow.movies.models.Movie;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interfaz de repositorio para realizar operaciones CRUD en la colección "movies" en MongoDB.
 * Extiende MongoRepository de Spring Data para aprovechar métodos preconfigurados para interacciones con la base de datos.

 * Métodos:
 * - findByImdbId: Recupera un documento de película basado en su identificador IMDb.

 * Anotaciones:
 * - @Repository: Indica que esta interfaz es un repositorio de Spring para inyección de dependencias.
 */

@Repository
public interface MovieRepository extends MongoRepository<Movie, ObjectId> {

    Optional<Movie> findByImdbId(String imdbId);
}


