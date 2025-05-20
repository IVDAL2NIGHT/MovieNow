package com.movieNow.movies.models;

public record LoginResponse(String token, String username, String email) {
}
