package com.leverx.ratingsystem.dto.auth;

public record ResetPasswordRequest(String email, String confirmationCode, String newPassword) {
}
