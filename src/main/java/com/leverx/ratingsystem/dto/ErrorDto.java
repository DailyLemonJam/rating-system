package com.leverx.ratingsystem.dto;

import jakarta.validation.constraints.NotNull;

public record ErrorDto(@NotNull String message) {
}
