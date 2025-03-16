package com.leverx.ratingsystem.dto.jwt;

import jakarta.validation.constraints.NotNull;

public record JwtRequest(@NotNull String username, @NotNull String password) {
}
