package com.leverx.ratingsystem.repository;

import com.leverx.ratingsystem.model.rating.Rating;
import com.leverx.ratingsystem.model.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface RatingRepository extends CrudRepository<Rating, UUID> {
    Optional<Rating> findByUserId(UUID userId);
}
