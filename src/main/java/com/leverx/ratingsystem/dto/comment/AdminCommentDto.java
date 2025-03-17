package com.leverx.ratingsystem.dto.comment;

import com.leverx.ratingsystem.model.comment.CommentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

public record AdminCommentDto(@NotBlank UUID userId,
                              @NotBlank String message,
                              @NotNull Integer grade,
                              @NotBlank Instant createdAt,
                              @NotBlank CommentStatus currentStatus,
                              @NotNull Integer editCount) {
}
