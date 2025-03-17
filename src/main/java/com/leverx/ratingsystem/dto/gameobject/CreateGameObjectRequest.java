package com.leverx.ratingsystem.dto.gameobject;

import jakarta.validation.constraints.NotNull;

public record CreateGameObjectRequest(@NotNull String title,
                                      @NotNull String description,
                                      @NotNull Integer gameId) {
}
