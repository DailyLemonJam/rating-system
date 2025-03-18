package com.leverx.ratingsystem.service;

import com.leverx.ratingsystem.dto.comment.AdminCommentDto;
import com.leverx.ratingsystem.dto.user.AdminUserDto;
import com.leverx.ratingsystem.exception.CommentNotFoundException;
import com.leverx.ratingsystem.exception.UserNotFoundException;
import com.leverx.ratingsystem.mapper.ModelDtoMapper;
import com.leverx.ratingsystem.model.comment.Comment;
import com.leverx.ratingsystem.model.comment.CommentStatus;
import com.leverx.ratingsystem.model.rating.Rating;
import com.leverx.ratingsystem.model.user.User;
import com.leverx.ratingsystem.model.user.UserStatus;
import com.leverx.ratingsystem.repository.CommentRepository;
import com.leverx.ratingsystem.repository.RatingRepository;
import com.leverx.ratingsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ModelDtoMapper<AdminCommentDto, Comment> adminCommentMapper;
    private final ModelDtoMapper<AdminUserDto, User> adminUserMapper;
    private final RatingService ratingService;
    private final RatingRepository ratingRepository;

    @Transactional(readOnly = true)
    public List<AdminCommentDto> getPendingComments() {
        var pendingComments = commentRepository.findAllByStatus(CommentStatus.PENDING);
        return adminCommentMapper.toDto(pendingComments);
    }

    @Transactional
    public void approveComment(UUID commentId) {
        var comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));
        comment.setStatus(CommentStatus.APPROVED);
        commentRepository.save(comment);
        ratingService.recalculateRatingByUserId(comment.getUser().getId());
    }

    @Transactional
    public void rejectComment(UUID commentId) {
        var comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));
        comment.setStatus(CommentStatus.REJECTED);
        commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public List<AdminUserDto> getPendingUsers() {
        var pendingUsers = userRepository.findAllByStatus(UserStatus.PENDING);
        return adminUserMapper.toDto(pendingUsers);
    }

    @Transactional
    public void approveUser(UUID userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setStatus(UserStatus.APPROVED);
        userRepository.save(user);
        if (ratingRepository.findByUser_Id(userId).isEmpty()) {
            var rating = Rating.builder()
                    .user(user)
                    .averageRating(0)
                    .totalRatings(0)
                    .build();
            ratingRepository.save(rating);
        }
    }

    @Transactional
    public void rejectUser(UUID userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setStatus(UserStatus.REJECTED);
        userRepository.save(user);
    }
}
