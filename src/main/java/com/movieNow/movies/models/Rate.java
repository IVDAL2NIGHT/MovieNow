package com.movieNow.movies.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "rates")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Rate {

    @Id
    private ObjectId id;
    private String imdbId;
    private String Title;
    private String username;
    private int rate;

    private User user;

    public Rate(String imdbId, String title, String username, int rate) {
        this.imdbId = imdbId;
        Title = title;
        this.username = username;
        this.rate = rate;
    }
}
