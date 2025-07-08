package com.meowlove.profileservice.exception.profile;

public class ProfileValidationException extends RuntimeException {
    public ProfileValidationException(String message) {
        super(message);
    }
}
