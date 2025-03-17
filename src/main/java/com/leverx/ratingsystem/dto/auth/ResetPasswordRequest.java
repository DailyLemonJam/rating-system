package com.leverx.ratingsystem.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record ResetPasswordRequest(@NotNull @Email String email,
                                   @NotNull String confirmationCode,
                                   @NotNull String newPassword) {
}
