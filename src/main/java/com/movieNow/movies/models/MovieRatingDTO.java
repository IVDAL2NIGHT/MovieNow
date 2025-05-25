package com.movieNow.movies.models;

public record MovieRatingDTO(
        String imdbId,
        String title,
        String rates,
        double averageRating,
        int totalRatings
) {
}



