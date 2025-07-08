package com.meowlove.profileservice.controller;

import com.meowlove.profileservice.exception.profile.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotUniqueException.class)
    public ResponseEntity<ProfileErrorResponse> handleUsernameNotUnique(UsernameNotUniqueException ex) {
        return ResponseEntity.badRequest().body(
                new ProfileErrorResponse(
                        HttpStatus.CONFLICT.value(),
                        "Username unique conflict",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(EmailNotUniqueException.class)
    public ResponseEntity<ProfileErrorResponse> handleEmailNotUnique(EmailNotUniqueException ex) {
        return ResponseEntity.badRequest().body(
                new ProfileErrorResponse(
                        HttpStatus.CONFLICT.value(),
                        "Email unique conflict",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(ProfileValidationException.class)
    public ResponseEntity<ProfileErrorResponse> handleProfileValidation(ProfileValidationException ex) {
        return ResponseEntity.badRequest().body(
                new ProfileErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        "Profile validation conflict",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(ProfilePasswordValidationException.class)
    public ResponseEntity<ProfileErrorResponse> handleProfilePasswordValidation(ProfilePasswordValidationException ex) {
        return ResponseEntity.badRequest().body(
                new ProfileErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        "Profile password validation conflict",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(ProfileNotFoundException.class)
    public ResponseEntity<ProfileErrorResponse> handleProfileNotFound(ProfileNotFoundException ex) {
        return ResponseEntity.badRequest().body(
                new ProfileErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        "Profile not found",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<ProfileErrorResponse> handleIncorrectPassword(IncorrectPasswordException ex) {
        return ResponseEntity.badRequest().body(
                new ProfileErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        "Incorrect old password",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    // TODO: сделать корректную обработку исключений некорректного UUID
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProfileErrorResponse> handleIncorrectPassword(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(
                new ProfileErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        "Incorrect UUID",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }
}
