package com.leverx.ratingsystem.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record VerifyUserEmailRequest(@NotBlank @Email String email,
                                     @NotBlank String confirmationCode) {
}
