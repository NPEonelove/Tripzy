package com.meowlove.profileservice.exception.profile;

public class EmailNotUniqueException extends RuntimeException {
    public EmailNotUniqueException(String message) {
        super(message);
    }
}
