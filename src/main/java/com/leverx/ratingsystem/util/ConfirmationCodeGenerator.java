package com.leverx.ratingsystem.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ConfirmationCodeGenerator {

    public String generateConfirmationCode() {
        return UUID.randomUUID().toString();
    }

}
