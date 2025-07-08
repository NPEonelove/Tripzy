package com.meowlove.profileservice.exception.profile;

public class ProfilePasswordValidationException extends RuntimeException {
    public ProfilePasswordValidationException(String message) {
        super(message);
    }
}
