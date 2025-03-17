package com.leverx.ratingsystem.exception;

public class UserNotAllowedToLoginException extends RuntimeException {

    public UserNotAllowedToLoginException(String message) {
        super(message);
    }

}
