package com.leverx.ratingsystem.dto.user;

import jakarta.validation.constraints.NotNull;

public record UserDto(@NotNull String username,
                      @NotNull String email) {
}
