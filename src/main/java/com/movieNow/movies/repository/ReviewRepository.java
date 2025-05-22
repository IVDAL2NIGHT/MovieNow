package com.movieNow.movies.repository;

import com.movieNow.movies.models.Review;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaz para gestionar operaciones CRUD en la colección "reviews" en MongoDB.
 * Extiende la interfaz MongoRepository proporcionada por Spring Data para aprovechar
 * los métodos predeterminados para interacciones con la base de datos.

 * Responsabilidades:
 * - Permite la gestión de objetos Review en la base de datos MongoDB.
 * - Proporciona métodos para guardar, eliminar y consultar documentos Review.

 * Anotaciones:
 * - @Repository: Especifica que esta interfaz es una capa de repositorio y la hace
 *   elegible para el mecanismo de traducción de excepciones e inyección de dependencias de Spring.

 * Herencia:
 * - Extiende MongoRepository con el tipo de entidad Review y el tipo de ID ObjectId,
 *   habilitando operaciones de base de datos específicas del tipo.
 */

@Repository
public interface ReviewRepository extends MongoRepository<Review, ObjectId> {
}
