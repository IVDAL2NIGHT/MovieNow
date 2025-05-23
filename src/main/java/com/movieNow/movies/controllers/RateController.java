package com.movieNow.movies.controllers;

import com.movieNow.movies.models.Rate;
import com.movieNow.movies.services.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/rates")


public class RateController {

    @Autowired
    private RateService rateService;

    @PostMapping
    public ResponseEntity<Rate> createRate(@RequestBody RateRequest request) {
        Rate rate = rateService.createrate(
                request.imdbId(),
                request.username(),
                request.rating()
        );
        return ResponseEntity.ok(rate);
    }
}
record RateRequest(String imdbId, String username, int rating) {}

