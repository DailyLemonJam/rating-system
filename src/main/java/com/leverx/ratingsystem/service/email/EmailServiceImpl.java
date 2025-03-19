package com.leverx.ratingsystem.service.email;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;

    @Override
    public void sendConfirmationCode(String to, String subject, String confirmationCode) {
        var message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText("Your confirmation code: " + confirmationCode);
        mailSender.send(message);
    }

}
