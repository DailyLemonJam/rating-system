package com.leverx.ratingsystem.dto.user;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

public record AdminUserDto(@NotNull UUID userId,
                           @NotNull String name,
                           @NotNull String email,
                           @NotNull Instant createdAt) {
}
