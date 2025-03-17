package com.leverx.ratingsystem.dto.user;

import jakarta.validation.constraints.NotBlank;

public record CreateUserResponse(@NotBlank String message) {
}
