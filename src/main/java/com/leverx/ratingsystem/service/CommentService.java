package com.leverx.ratingsystem.service;

import com.leverx.ratingsystem.dto.comment.AdminCommentDto;
import com.leverx.ratingsystem.dto.comment.CommentDto;
import com.leverx.ratingsystem.dto.comment.UpdateCommentRequest;
import com.leverx.ratingsystem.exception.CommentNotFoundException;
import com.leverx.ratingsystem.mapper.ModelDtoMapper;
import com.leverx.ratingsystem.model.comment.Comment;
import com.leverx.ratingsystem.model.comment.CommentStatus;
import com.leverx.ratingsystem.repository.CommentRepository;
import com.leverx.ratingsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ModelDtoMapper<CommentDto, Comment> commentMapper;
    private final ModelDtoMapper<AdminCommentDto, Comment> adminCommentMapper;

    @Transactional(readOnly = true)
    public CommentDto getCommentById(UUID commentId) {
        var comment = commentRepository.findByIdAndStatus(commentId, CommentStatus.APPROVED)
                .orElseThrow(() -> new CommentNotFoundException("Can't find comment with id: " + commentId));
        return commentMapper.toDto(comment);
    }

    @Transactional
    public CommentDto updateCommentById(UUID commentId, UpdateCommentRequest updateCommentRequest) {
        var comment = commentRepository.findById(commentId).
                orElseThrow(() -> new CommentNotFoundException("Can't find comment with id: " + commentId));
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

}
