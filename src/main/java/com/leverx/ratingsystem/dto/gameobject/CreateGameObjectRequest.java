package com.leverx.ratingsystem.dto.gameobject;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateGameObjectRequest(@NotBlank String title,
                                      @NotBlank String description,
                                      @NotNull Integer gameId) {
}
