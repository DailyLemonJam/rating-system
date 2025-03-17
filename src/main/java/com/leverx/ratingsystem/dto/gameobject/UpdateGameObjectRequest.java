package com.leverx.ratingsystem.dto.gameobject;

import jakarta.validation.constraints.NotBlank;

public record UpdateGameObjectRequest(@NotBlank String newTitle,
                                      @NotBlank String newDescription) {
}
