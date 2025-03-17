package com.leverx.ratingsystem.dto.user;

import jakarta.validation.constraints.NotBlank;

public record UserDto(@NotBlank String username,
                      @NotBlank String email) {
}
