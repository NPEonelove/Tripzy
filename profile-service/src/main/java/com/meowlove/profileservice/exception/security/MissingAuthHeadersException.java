package com.meowlove.profileservice.exception.security;

public class MissingAuthHeadersException extends RuntimeException {
    public MissingAuthHeadersException(String message) {
        super(message);
    }
}
