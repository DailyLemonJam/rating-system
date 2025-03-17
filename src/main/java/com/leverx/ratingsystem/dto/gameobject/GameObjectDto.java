package com.leverx.ratingsystem.dto.gameobject;

import jakarta.validation.constraints.NotBlank;

import java.time.Instant;
import java.util.UUID;

public record GameObjectDto(@NotBlank String title,
                            @NotBlank String description,
                            @NotBlank Instant createdAt,
                            @NotBlank Instant updatedAt,
                            @NotBlank UUID userId) {
}
