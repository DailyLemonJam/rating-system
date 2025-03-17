package com.leverx.ratingsystem.dto.gameobject;

import jakarta.validation.constraints.NotNull;

public record UpdateGameObjectRequest(@NotNull String newTitle,
                                      @NotNull String newDescription) {
}
