package com.leverx.ratingsystem.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RatingDto(@NotNull UUID userId,
                        @NotNull Double averageRating) {
}
