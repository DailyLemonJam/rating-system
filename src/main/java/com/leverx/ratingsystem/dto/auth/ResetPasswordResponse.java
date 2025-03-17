package com.leverx.ratingsystem.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record ResetPasswordResponse(@NotBlank String message) {
}
