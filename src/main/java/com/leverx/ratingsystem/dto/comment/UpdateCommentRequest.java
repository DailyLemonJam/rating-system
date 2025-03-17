package com.leverx.ratingsystem.dto.comment;

import com.leverx.ratingsystem.config.AppConfiguration;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateCommentRequest(@NotNull String newMessage,
                                   @NotNull @Min(AppConfiguration.MIN_GRADE)
                                   @NotNull @Max(AppConfiguration.MAX_GRADE) Integer newGrade) {
}
