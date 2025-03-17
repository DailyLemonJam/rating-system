package com.leverx.ratingsystem.dto.comment;

import com.leverx.ratingsystem.config.AppConfiguration;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateCommentRequest(@NotBlank String message,
                                   @NotBlank @Min(AppConfiguration.MIN_GRADE)
                                   @NotBlank @Max(AppConfiguration.MAX_GRADE) Integer grade) {
}
