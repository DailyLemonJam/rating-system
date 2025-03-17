package com.leverx.ratingsystem.dto.auth;

import jakarta.validation.constraints.NotNull;

public record ResetPasswordResponse(@NotNull String message) {
}
