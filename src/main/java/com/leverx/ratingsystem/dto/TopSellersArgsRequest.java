package com.leverx.ratingsystem.dto;

import com.leverx.ratingsystem.config.AppConfiguration;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record TopSellersArgsRequest(@NotNull
                                    @Min(AppConfiguration.MIN_GRADE)
                                    @Max(AppConfiguration.MAX_GRADE) Double minRating,
                                    @NotNull
                                    @Min(AppConfiguration.MIN_GRADE)
                                    @Max(AppConfiguration.MAX_GRADE) Double maxRating) {
}
