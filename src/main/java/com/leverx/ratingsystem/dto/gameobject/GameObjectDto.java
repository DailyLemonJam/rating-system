package com.leverx.ratingsystem.dto.gameobject;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

public record GameObjectDto(@NotNull String title,
                            @NotNull String description,
                            @NotNull Instant createdAt,
                            @NotNull Instant updatedAt,
                            @NotNull UUID userId) {
}
