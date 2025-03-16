package com.leverx.ratingsystem.exception;

public class IncorrectUsernameOrPasswordException extends RuntimeException {

    public IncorrectUsernameOrPasswordException(String message) {
        super(message);
    }

}
