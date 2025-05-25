package com.movieNow.movies.repository;

import com.movieNow.movies.models.Movie;
import com.movieNow.movies.models.MovieList;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieListRepository extends MongoRepository<MovieList, ObjectId> {
}
