package com.leverx.ratingsystem.exception;

public class UserRatingNotFoundException extends RuntimeException {

    public UserRatingNotFoundException(String message) {
        super(message);
    }

}
