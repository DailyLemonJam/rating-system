package com.leverx.ratingsystem.dto.comment;

import java.time.LocalDateTime;
import java.util.UUID;

public record CommentDto(String message, int grade, LocalDateTime createdAt, UUID sellerId) {
}
