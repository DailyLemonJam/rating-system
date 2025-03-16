package com.leverx.ratingsystem.service;

import com.leverx.ratingsystem.dto.comment.CommentDto;
import com.leverx.ratingsystem.dto.comment.CreateCommentRequest;
import com.leverx.ratingsystem.dto.comment.UpdateCommentRequest;
import com.leverx.ratingsystem.exception.CommentNotFoundException;
import com.leverx.ratingsystem.exception.NotAllowedToUpdateCommentException;
import com.leverx.ratingsystem.exception.UserNotFoundException;
import com.leverx.ratingsystem.mapper.CommentMapper;
import com.leverx.ratingsystem.model.comment.Comment;
import com.leverx.ratingsystem.model.comment.CommentStatus;
import com.leverx.ratingsystem.repository.CommentRepository;
import com.leverx.ratingsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final CommentMapper commentMapper;

    @Transactional(readOnly = true)
    public CommentDto getCommentById(UUID commentId) {
        var comment = commentRepository.findById(commentId)
                .filter(com -> com.getStatus() == CommentStatus.APPROVED
                        || com.getStatus() == CommentStatus.MODIFIED_AND_APPROVED)
                .orElseThrow(() -> new CommentNotFoundException("Can't find comment with id: " + commentId));
        return commentMapper.toDto(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentDto> getAllCommentsByUserId(UUID userId) {
        userService.findById(userId).
                //filter(user -> user.getRole() == RoleEnum.SELLER).
                orElseThrow(() -> new UserNotFoundException("Can't find user with id: " + userId));
        var approvedComments = commentRepository.findByUserIdAndStatus(userId, CommentStatus.APPROVED);
        var modifiedAndApprovedComments = commentRepository.findByUserIdAndStatus(userId, CommentStatus.MODIFIED_AND_APPROVED);
        approvedComments.addAll(modifiedAndApprovedComments);
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
        if (commentStatus == CommentStatus.MODIFIED_AND_PENDING
                || commentStatus == CommentStatus.MODIFIED_AND_APPROVED
                || commentStatus == CommentStatus.MODIFIED_AND_REJECTED) {
            throw new NotAllowedToUpdateCommentException("Comment was already updated once and can't be updated again.");
        }
        comment.setMessage(updateCommentRequest.newMessage());
        comment.setGrade(updateCommentRequest.newGrade());
        comment.setStatus(CommentStatus.MODIFIED_AND_PENDING);
        commentRepository.save(comment);
        // TODO: tell RatingService to update related Seller rating accordingly
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

}
