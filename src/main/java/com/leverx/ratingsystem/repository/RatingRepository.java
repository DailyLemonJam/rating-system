package com.leverx.ratingsystem.repository;

import com.leverx.ratingsystem.model.rating.Rating;
import com.leverx.ratingsystem.model.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RatingRepository extends CrudRepository<Rating, UUID> {
    Optional<Rating> findByUser(User user);
    Optional<Rating> findByUser_Id(UUID userId);
    List<Rating> findAllByAverageRatingBetweenOrderByAverageRatingDesc(Double min, Double max);
}
