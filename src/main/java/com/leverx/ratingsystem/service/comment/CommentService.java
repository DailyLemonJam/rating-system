package com.leverx.ratingsystem.service.comment;

import com.leverx.ratingsystem.dto.comment.*;

import java.util.List;
import java.util.UUID;

public interface CommentService {
    List<CommentDto> getAllComments(UUID userId, String keyword);

    CommentDto getCommentById(UUID commentId);

    CommentDto createCommentOnSellerProfile(UUID userId, CreateCommentRequest createCommentRequest);

    CommentDto createCommentWithSellerProfile(CreateCommentWithSellerRequest request);

    CommentDto updateCommentById(UUID commentId, UpdateCommentRequest request);

    void deleteCommentById(UUID commentId, DeleteCommentRequest request);
}
