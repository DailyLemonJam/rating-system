package com.leverx.ratingsystem.dto.comment;

import java.time.Instant;
import java.util.UUID;

public record CommentDto(String message, int grade, Instant createdAt, UUID sellerId) {
}
