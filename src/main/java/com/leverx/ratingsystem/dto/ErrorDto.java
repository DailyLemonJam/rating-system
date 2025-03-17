package com.leverx.ratingsystem.dto;

import jakarta.validation.constraints.NotBlank;

public record ErrorDto(@NotBlank String message) {
}
