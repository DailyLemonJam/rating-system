package com.leverx.ratingsystem.dto.auth;

public record VerifyUserEmailRequest(String email, String confirmationCode) {
}
