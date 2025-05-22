package com.movieNow.movies.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

/**
 * Representa un documento de película almacenado en la colección "movies".
 * Esta clase sirve como modelo de datos para películas con campos que contienen
 * metadatos sobre una película, como título, información de lanzamiento, géneros, trailers,
 * pósters y reseñas relacionadas.

 * Anotaciones:
 * - @Document: Mapea esta clase a la colección "movies" en la base de datos.
 * - @Data: Genera automáticamente los métodos getter, setter, equals, hashCode y toString.
 * - @AllArgsConstructor: Genera un constructor con todos los campos como parámetros.
 * - @NoArgsConstructor: Genera un constructor sin argumentos.

 * Campos:
 * - id: Identificador único para el documento de la película.
 * - imdbId: Identificador externo de la película asociado con IMDb.
 * - title: Título de la película.
 * - releaseDate: Fecha de lanzamiento de la película.
 * - trailerLink: URL que apunta al trailer de la película.
 * - poster: URL que apunta a una imagen del póster de la película.
 * - genres: Lista de géneros que describen el contenido de la película.
 * - backdrops: Lista de URLs que apuntan a imágenes de fondo o fotogramas de la película.
 * - reviews: Lista de reseñas asociadas con la película, que contienen comentarios de usuarios.
 */

@Document(collection = "movies")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @Id
    private ObjectId id;

    private String imdbId;

    private String title;

    private String releaseDate;

    private String trailerLink;

    private String poster;

    private List<String> genres;

    private List<String> backdrops;

    private List<Review> reviews;


}