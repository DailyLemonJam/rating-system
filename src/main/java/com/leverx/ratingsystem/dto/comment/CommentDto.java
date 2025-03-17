package com.leverx.ratingsystem.dto.comment;

import com.leverx.ratingsystem.config.AppConfiguration;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

public record CommentDto(@NotBlank String message,
                         @NotNull @Min(AppConfiguration.MIN_GRADE)
                         @NotNull @Max(AppConfiguration.MAX_GRADE) Integer grade,
                         @NotBlank Instant createdAt,
                         @NotBlank UUID sellerId) {
}
