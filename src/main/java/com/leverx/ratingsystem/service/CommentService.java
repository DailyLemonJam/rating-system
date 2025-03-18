package com.leverx.ratingsystem.service;

import com.leverx.ratingsystem.dto.comment.AdminCommentDto;
import com.leverx.ratingsystem.dto.comment.CommentDto;
import com.leverx.ratingsystem.dto.comment.CreateCommentWithSellerRequest;
import com.leverx.ratingsystem.dto.comment.UpdateCommentRequest;
import com.leverx.ratingsystem.exception.CommentNotFoundException;
import com.leverx.ratingsystem.exception.UserAlreadyExistsException;
import com.leverx.ratingsystem.mapper.ModelDtoMapper;
import com.leverx.ratingsystem.model.comment.Comment;
import com.leverx.ratingsystem.model.comment.CommentStatus;
import com.leverx.ratingsystem.model.rating.Rating;
import com.leverx.ratingsystem.model.user.User;
import com.leverx.ratingsystem.model.user.UserEmailStatus;
import com.leverx.ratingsystem.model.user.UserStatus;
import com.leverx.ratingsystem.repository.CommentRepository;
import com.leverx.ratingsystem.repository.RatingRepository;
import com.leverx.ratingsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelDtoMapper<CommentDto, Comment> commentMapper;
    private final ModelDtoMapper<AdminCommentDto, Comment> adminCommentMapper;
    private final RatingRepository ratingRepository;
    private final RatingService ratingService;

    @Transactional(readOnly = true)
    public CommentDto getCommentById(UUID commentId) {
        var comment = commentRepository.findByIdAndStatus(commentId, CommentStatus.APPROVED)
                .orElseThrow(() -> new CommentNotFoundException("Can't find comment with id: " + commentId));
        return commentMapper.toDto(comment);
    }

    @Transactional
    public CommentDto createCommentWithSeller(CreateCommentWithSellerRequest request) {
        if (userRepository.existsByName(request.sellerName())) {
            throw new UserAlreadyExistsException("This seller already exists");
        }
        var newSeller = User.builder()
                .name(request.sellerName())
                .createdAt(Instant.now())
                .roles(List.of(roleService.getUserRole()))
                .status(UserStatus.PENDING)
                .emailStatus(UserEmailStatus.NOT_SET)
                .build();
        userRepository.save(newSeller);
        var comment = Comment.builder()
                .message(request.message())
                .grade(request.grade())
                .user(newSeller)
                .createdAt(Instant.now())
                .status(CommentStatus.PENDING)
                .editCount(0)
                .build();
        commentRepository.save(comment);
        var rating = Rating.builder()
                .user(newSeller)
                .build();
        ratingRepository.save(rating);
        ratingService.recalculateUserRatingByUserId(rating.getUser().getId());
        return commentMapper.toDto(comment);
    }

    @Transactional
    public CommentDto updateCommentById(UUID commentId, UpdateCommentRequest updateCommentRequest) {
        var comment = commentRepository.findById(commentId).
                orElseThrow(() -> new CommentNotFoundException("Can't find comment with id: " + commentId));
        comment.setMessage(updateCommentRequest.newMessage());
        comment.setGrade(updateCommentRequest.newGrade());
        comment.setStatus(CommentStatus.PENDING);
        comment.setEditCount(comment.getEditCount() + 1);
        commentRepository.save(comment);
        ratingService.recalculateUserRatingByUserId(comment.getUser().getId());
        return commentMapper.toDto(comment);
    }

    @Transactional
    public void deleteCommentById(UUID commentId) {
        var comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Can't find comment"));
        var userId = comment.getUser().getId();
        commentRepository.deleteById(commentId);
        ratingService.recalculateUserRatingByUserId(userId);
    }

}
