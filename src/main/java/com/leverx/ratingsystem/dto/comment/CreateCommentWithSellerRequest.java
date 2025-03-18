package com.leverx.ratingsystem.dto.comment;

import com.leverx.ratingsystem.config.AppConfiguration;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCommentWithSellerRequest(@NotBlank String message,
                                             @NotBlank String password,
                                             @NotNull @Min(AppConfiguration.MIN_GRADE)
                                             @NotNull @Max(AppConfiguration.MAX_GRADE) Integer grade,
                                             @NotBlank String sellerName) {
}
