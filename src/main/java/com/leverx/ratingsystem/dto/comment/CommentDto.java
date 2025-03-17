package com.leverx.ratingsystem.dto.comment;

import com.leverx.ratingsystem.config.AppConfiguration;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

public record CommentDto(@NotNull String message,
                         @NotNull @Min(AppConfiguration.MIN_GRADE)
                         @NotNull @Max(AppConfiguration.MAX_GRADE) Integer grade,
                         @NotNull Instant createdAt,
                         @NotNull UUID sellerId) {
}
