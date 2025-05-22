package com.movieNow.movies.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

/**
 * Representa un documento de reseña almacenado en la colección "reviews".
 * Esta clase modela una reseña con campos para el contenido de la reseña,
 * el nombre de usuario del revisor y un objeto de usuario asociado opcional.

 * Anotaciones:
 * - @Document: Mapea esta clase a la colección "reviews" en la base de datos.
 * - @Data: Genera automáticamente los métodos getter, setter, equals, hashCode y toString.
 * - @AllArgsConstructor: Genera un constructor con todos los campos como parámetros.
 * - @NoArgsConstructor: Genera un constructor sin argumentos.

 * Campos:
 * - id: Identificador único para el documento de la reseña.
 * - body: El contenido o texto de la reseña.
 * - username: El nombre de usuario de la persona que envía la reseña.
 * - user: El objeto de usuario asociado que contiene detalles adicionales del usuario.

 * Constructores:
 * - Review(String body, String username): Crea una instancia de reseña con el contenido y nombre de usuario especificados.
 */

@Document(collection = "reviews")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    
    @Id
    private ObjectId id;
    private String body;
    private String username;


    private User user;
    
    public Review(String body, String username) {
        this.body = body;
        this.username = username;
    }
}