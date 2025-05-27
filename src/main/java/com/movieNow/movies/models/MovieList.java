package com.movieNow.movies.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa una colección de películas organizada por un usuario, almacenada en la colección "movieLists" en una base de datos MongoDB.
 * Esta clase modela una lista personalizada de películas de un usuario, incluyendo metadatos como título, descripción y el usuario que la creó.
 *
 * Anotaciones:
 * - @Document: Mapea esta clase a la colección "movieLists" en la base de datos.
 * - @Data: Genera automáticamente los métodos getter, setter, equals, hashCode y toString.
 * - @AllArgsConstructor: Crea un constructor con todos los campos como parámetros.
 * - @NoArgsConstructor: Crea un constructor sin argumentos.
 *
 * Campos:
 * - id: El identificador único de esta lista de películas en MongoDB.
 * - title: El título de la lista de películas.
 * - description: Una descripción para la lista de películas.
 * - username: El nombre de usuario del creador de la lista.
 * - user: El objeto Usuario asociado con el creador de la lista.
 * - movies: Una lista de objetos Movie que forman parte de esta lista de películas.
 *
 * Constructores:
 * - MovieList(String username, String title, String description): Inicializa una lista de películas con el nombre de usuario, título, descripción dados y una lista vacía de películas.
 */
@Document(collection = "movieLists")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieList {

    @Id
    private ObjectId id;
    private String title;
    private String description;
    private String username;
    private User user;
    private List<Movie> movies;

    public MovieList(String username, String title, String description) {
        this.username = username;
        this.title = title;
        this.description = description;
        this.movies = new ArrayList<>();
    }
}