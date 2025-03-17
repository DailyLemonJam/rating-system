package com.leverx.ratingsystem.dto.auth;

import jakarta.validation.constraints.NotNull;

public record ForgotPasswordResponse(@NotNull String message) {
}
