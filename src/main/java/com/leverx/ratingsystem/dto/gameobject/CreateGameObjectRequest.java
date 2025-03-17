package com.leverx.ratingsystem.dto.gameobject;

import jakarta.validation.constraints.NotBlank;

public record CreateGameObjectRequest(@NotBlank String title,
                                      @NotBlank String description,
                                      @NotBlank Integer gameId) {
}
