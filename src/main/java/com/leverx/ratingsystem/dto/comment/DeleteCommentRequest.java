package com.leverx.ratingsystem.dto.comment;

import jakarta.validation.constraints.NotNull;

public record DeleteCommentRequest(@NotNull String password) {
}
