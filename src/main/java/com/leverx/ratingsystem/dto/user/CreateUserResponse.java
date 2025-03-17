package com.leverx.ratingsystem.dto.user;

import jakarta.validation.constraints.NotNull;

public record CreateUserResponse(@NotNull String message) {
}
