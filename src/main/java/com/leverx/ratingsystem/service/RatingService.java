package com.leverx.ratingsystem.service;

import com.leverx.ratingsystem.dto.RatingDto;
import com.leverx.ratingsystem.exception.UserRatingNotFoundException;
import com.leverx.ratingsystem.mapper.ModelDtoMapper;
import com.leverx.ratingsystem.model.comment.Comment;
import com.leverx.ratingsystem.model.rating.Rating;
import com.leverx.ratingsystem.repository.CommentRepository;
import com.leverx.ratingsystem.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final CommentRepository commentRepository;
    private final ModelDtoMapper<RatingDto, Rating> ratingMapper;

    @Transactional
    public void recalculateUserRatingByUserId(UUID userId) {
        var rating = ratingRepository.findByUser_Id(userId)
                .orElseThrow(() -> new UserRatingNotFoundException("Can't find user or his rating"));
        var comments = commentRepository.findAllByUser_Id(userId);
        double averageRating = comments.stream()
                .mapToInt(Comment::getGrade)
                .average()
                .orElse(0.0);
        rating.setAverageRating(averageRating);
        rating.setTotalRatings(comments.size());
        ratingRepository.save(rating);
    }

}
