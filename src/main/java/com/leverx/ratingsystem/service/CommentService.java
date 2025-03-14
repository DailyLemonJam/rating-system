package com.leverx.ratingsystem.service;

import com.leverx.ratingsystem.dto.comment.CommentDto;
import com.leverx.ratingsystem.dto.comment.UpdateCommentRequest;
import com.leverx.ratingsystem.exception.CommentNotFoundException;
import com.leverx.ratingsystem.exception.NotAllowedToUpdateCommentException;
import com.leverx.ratingsystem.mapper.CommentMapper;
import com.leverx.ratingsystem.model.comment.CommentStatus;
import com.leverx.ratingsystem.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public CommentService(CommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    public CommentDto getCommentById(UUID id) {
        var comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Can't find comment with id: " + id));
        return commentMapper.toDto(comment);
    }

    @Transactional
    public CommentDto updateCommentById(UUID id, UpdateCommentRequest updateCommentRequest) {
        var comment = commentRepository.findById(id).
                orElseThrow(() -> new CommentNotFoundException("Can't find comment with id: " + id));
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
    public void deleteCommentById(UUID id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
            return;
        }
        throw new CommentNotFoundException("Can't find comment with id: " + id);
    }

}
