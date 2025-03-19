package com.leverx.ratingsystem.service.profile;

import com.leverx.ratingsystem.dto.RatingDto;
import com.leverx.ratingsystem.dto.comment.CommentDto;
import com.leverx.ratingsystem.dto.gameobject.GameObjectDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface SellerProfileService {
    List<RatingDto> getTopRatingsSellers(double minRating, double maxRating);

    List<RatingDto> getSellerRatingsWithObjectsFromGame(Integer gameId);

    List<CommentDto> getCommentsByUserId(UUID userId);

    List<GameObjectDto> getAllGameObjectsByUserId(UUID userId);

    RatingDto getRatingByUserId(UUID userId);
}
