package com.leverx.ratingsystem.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record VerifyUserEmailRequest(@NotNull @Email String email,
                                     @NotNull String confirmationCode) {
}
