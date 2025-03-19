package com.leverx.ratingsystem.unit;

import com.leverx.ratingsystem.model.comment.Comment;
import com.leverx.ratingsystem.model.comment.CommentStatus;
import com.leverx.ratingsystem.model.rating.Rating;
import com.leverx.ratingsystem.repository.CommentRepository;
import com.leverx.ratingsystem.repository.RatingRepository;
import com.leverx.ratingsystem.service.rating.RatingServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RatingServiceImplTest {

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private RatingServiceImpl ratingService;

    @Test
    void updateUserRatingByUserId_success() {
        UUID userId = UUID.randomUUID();
        Rating mockRating = new Rating();
        mockRating.setAverageRating(0.0);
        mockRating.setTotalRatings(0);

        Comment approvedComment1 = new Comment();
        approvedComment1.setGrade(4);

        Comment approvedComment2 = new Comment();
        approvedComment2.setGrade(5);

        when(ratingRepository.findByUser_Id(userId)).thenReturn(Optional.of(mockRating));
        when(commentRepository.findAllByUser_IdAndStatus(userId, CommentStatus.APPROVED))
                .thenReturn(List.of(approvedComment1, approvedComment2));

        ratingService.updateUserRatingByUserId(userId);

        assertEquals(4.5, mockRating.getAverageRating());
        assertEquals(2, mockRating.getTotalRatings());
    }
}