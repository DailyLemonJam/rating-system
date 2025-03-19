package com.leverx.ratingsystem.service.email;

public interface EmailService {
    void sendConfirmationCode(String to, String subject, String confirmationCode);
}
