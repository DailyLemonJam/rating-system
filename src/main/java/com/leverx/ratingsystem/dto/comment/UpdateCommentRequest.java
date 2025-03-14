package com.leverx.ratingsystem.dto.comment;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateCommentRequest(@NotNull String newMessage,
                                   @Min(1) @Max(5) int newGrade) {
}
