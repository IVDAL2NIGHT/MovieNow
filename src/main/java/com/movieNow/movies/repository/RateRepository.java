package com.movieNow.movies.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.movieNow.movies.models.Rate;
import org.bson.types.ObjectId;
import java.util.List;

@Repository
public interface RateRepository extends MongoRepository<Rate, ObjectId> {
    List<Rate> findByImdbId(String imdbId);

}
