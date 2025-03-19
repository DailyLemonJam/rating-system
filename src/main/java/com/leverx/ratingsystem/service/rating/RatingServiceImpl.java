package com.leverx.ratingsystem.service.rating;

import com.leverx.ratingsystem.exception.UserRatingNotFoundException;
import com.leverx.ratingsystem.model.comment.Comment;
import com.leverx.ratingsystem.model.comment.CommentStatus;
import com.leverx.ratingsystem.repository.CommentRepository;
import com.leverx.ratingsystem.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;
    private final CommentRepository commentRepository;

    @Transactional
    @Override
    public void updateUserRatingByUserId(UUID userId) {
        var rating = ratingRepository.findByUser_Id(userId)
                .orElseThrow(() -> new UserRatingNotFoundException("Can't find user or his rating"));
        var comments = commentRepository.findAllByUser_IdAndStatus(userId, CommentStatus.APPROVED);
        double averageRating = comments.stream()
                .mapToInt(Comment::getGrade)
                .average()
                .orElse(0.0);
        rating.setAverageRating(averageRating);
        rating.setTotalRatings(comments.size());
        ratingRepository.save(rating);
    }

}
