package com.leverx.ratingsystem.service;

import com.leverx.ratingsystem.dto.comment.*;
import com.leverx.ratingsystem.exception.CommentNotFoundException;
import com.leverx.ratingsystem.exception.IncorrectCommentPasswordException;
import com.leverx.ratingsystem.exception.UserAlreadyExistsException;
import com.leverx.ratingsystem.exception.UserNotFoundException;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final RatingRepository ratingRepository;
    private final RatingService ratingService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<CommentDto> getAllComments(UUID userId, String keyword) {
        var comments = commentRepository.findAllByUser_IdAndMessageContainsIgnoreCase(userId, keyword);
        return commentMapper.toDto(comments);
    }

    @Transactional(readOnly = true)
    public CommentDto getCommentById(UUID commentId) {
        var comment = commentRepository.findByIdAndStatus(commentId, CommentStatus.APPROVED)
                .orElseThrow(() -> new CommentNotFoundException("Comment doesn't exist or wasn't approved"));
        return commentMapper.toDto(comment);
    }

    @Transactional
    public CommentDto createCommentOnSellerProfile(UUID userId, CreateCommentRequest createCommentRequest) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if (user.getStatus() != UserStatus.APPROVED) {
            throw new UserNotFoundException("User not found");
        }
        var comment = Comment.builder()
                .message(createCommentRequest.message())
                .encryptedPassword(passwordEncoder.encode(createCommentRequest.password()))
                .grade(createCommentRequest.grade())
                .user(user)
                .createdAt(Instant.now())
                .status(CommentStatus.PENDING)
                .editCount(0)
                .build();
        commentRepository.save(comment);
        return commentMapper.toDto(comment);
    }

    @Transactional
    public CommentDto createCommentWithSellerProfile(CreateCommentWithSellerRequest request) {
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
                .encryptedPassword(passwordEncoder.encode(request.password()))
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
        return commentMapper.toDto(comment);
    }

    @Transactional
    public CommentDto updateCommentById(UUID commentId, UpdateCommentRequest request) {
        var comment = commentRepository.findById(commentId).
                orElseThrow(() -> new CommentNotFoundException("Can't find comment with id: " + commentId));
        if (!comment.getEncryptedPassword().equals(passwordEncoder.encode(request.password()))) {
            throw new IncorrectCommentPasswordException("Incorrect password");
        }
        comment.setMessage(request.newMessage());
        comment.setGrade(request.newGrade());
        comment.setStatus(CommentStatus.PENDING);
        comment.setEditCount(comment.getEditCount() + 1);
        commentRepository.save(comment);
        return commentMapper.toDto(comment);
    }

    @Transactional
    public void deleteCommentById(UUID commentId, DeleteCommentRequest request) {
        var comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Can't find comment"));
        if (!comment.getEncryptedPassword().equals(passwordEncoder.encode(request.password()))) {
            throw new IncorrectCommentPasswordException("Incorrect password");
        }
        var userId = comment.getUser().getId();
        commentRepository.deleteById(commentId);
        ratingService.updateUserRatingByUserId(userId);
    }

}
