package com.movieNow.movies.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

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
