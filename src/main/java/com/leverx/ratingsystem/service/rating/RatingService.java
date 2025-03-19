package com.leverx.ratingsystem.service.rating;

import java.util.UUID;

public interface RatingService {
    void updateUserRatingByUserId(UUID userId);
}
