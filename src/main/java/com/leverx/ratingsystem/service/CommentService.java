package com.leverx.ratingsystem.service;

import com.leverx.ratingsystem.dto.comment.AdminCommentDto;
import com.leverx.ratingsystem.dto.comment.CommentDto;
import com.leverx.ratingsystem.dto.comment.CreateCommentRequest;
import com.leverx.ratingsystem.dto.comment.UpdateCommentRequest;
import com.leverx.ratingsystem.exception.CommentNotFoundException;
import com.leverx.ratingsystem.exception.NotAllowedToUpdateCommentException;
import com.leverx.ratingsystem.exception.UserNotFoundException;
import com.leverx.ratingsystem.mapper.ModelDtoMapper;
import com.leverx.ratingsystem.mapper.comment.AdminCommentMapper;
import com.leverx.ratingsystem.mapper.comment.CommentMapper;
import com.leverx.ratingsystem.model.comment.Comment;
import com.leverx.ratingsystem.model.comment.CommentStatus;
import com.leverx.ratingsystem.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ModelDtoMapper<CommentDto, Comment> commentMapper;
    private final ModelDtoMapper<AdminCommentDto, Comment> adminCommentMapper;

    public CommentService(CommentRepository commentRepository,
                          UserService userService,
                          CommentMapper commentMapper,
                          AdminCommentMapper adminCommentMapper) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.commentMapper = commentMapper;
        this.adminCommentMapper = adminCommentMapper;
    }

    @Transactional(readOnly = true)
    public CommentDto getCommentById(UUID commentId) {
        var comment = commentRepository.findById(commentId)
                .filter(com -> com.getStatus() == CommentStatus.APPROVED)
                .orElseThrow(() -> new CommentNotFoundException("Can't find comment with id: " + commentId));
        return commentMapper.toDto(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentDto> getAllCommentsByUserId(UUID userId) {
        userService.findById(userId).
                //filter(user -> user.getRole() == RoleEnum.SELLER).
                orElseThrow(() -> new UserNotFoundException("Can't find user with id: " + userId));
        var approvedComments = commentRepository.findByUserIdAndStatus(userId, CommentStatus.APPROVED);
        return commentMapper.toDto(approvedComments);
    }

    @Transactional
    public CommentDto createComment(UUID userId, CreateCommentRequest createCommentRequest) {
        var seller = userService.findById(userId).
                //filter(user -> user.getRole() == RoleEnum.SELLER).
                orElseThrow(() -> new UserNotFoundException("Can't find user with id: " + userId));
        var comment = Comment.builder()
                .user(seller)
                .message(createCommentRequest.message())
                .grade(createCommentRequest.grade())
                .createdAt(Instant.now())
                .status(CommentStatus.PENDING)
                .build();
        var newComment = commentRepository.save(comment);
        return commentMapper.toDto(newComment);
    }

    @Transactional
    public CommentDto updateCommentById(UUID commentId, UpdateCommentRequest updateCommentRequest) {
        var comment = commentRepository.findById(commentId).
                orElseThrow(() -> new CommentNotFoundException("Can't find comment with id: " + commentId));
        var commentStatus = comment.getStatus();
        if (commentStatus == CommentStatus.PENDING) {
            throw new NotAllowedToUpdateCommentException("Your last comment update wasn't approved or declined yet.");
        }
        comment.setMessage(updateCommentRequest.newMessage());
        comment.setGrade(updateCommentRequest.newGrade());
        comment.setStatus(CommentStatus.PENDING);
        commentRepository.save(comment);
        return commentMapper.toDto(comment);
    }

    @Transactional
    public void deleteCommentById(UUID commentId) {
        if (commentRepository.existsById(commentId)) {
            commentRepository.deleteById(commentId);
            return;
        }
        throw new CommentNotFoundException("Can't find comment with id: " + commentId);
    }

    @Transactional(readOnly = true)
    public List<AdminCommentDto> getCommentsWithStatus(CommentStatus commentStatus) {
        var pendingComments = commentRepository.findByStatus(commentStatus);
        return adminCommentMapper.toDto(pendingComments);
    }

    @Transactional
    public void changeCommentStatus(UUID commentId, CommentStatus newCommentStatus) {
        var comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Can't find comment with id: " + commentId));
        comment.setStatus(newCommentStatus);
        commentRepository.save(comment);
    }

}
