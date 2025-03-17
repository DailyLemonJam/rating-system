package com.leverx.ratingsystem.dto.auth;

import jakarta.validation.constraints.NotNull;

public record VerifyUserEmailResponse(@NotNull String message) {
}
