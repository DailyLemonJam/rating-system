package com.leverx.ratingsystem.service.confirmation;

public interface ConfirmationCodeService {
    void save(String confirmationCode, String email);

    String get(String confirmationCode);

    Boolean exists(String confirmationCode);

    void delete(String confirmationCode);
}
