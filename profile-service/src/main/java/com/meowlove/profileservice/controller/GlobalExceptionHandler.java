package com.meowlove.profileservice.controller;

import com.meowlove.profileservice.exception.ErrorResponse;
import com.meowlove.profileservice.exception.profile.ProfileNotFoundException;
import com.meowlove.profileservice.exception.profile.ProfileValidationException;
import com.meowlove.profileservice.exception.profile.UserIDNotUniqueException;
import com.meowlove.profileservice.exception.profile.UsernameNotUniqueException;
import com.meowlove.profileservice.exception.security.MissingAuthHeadersException;
import com.meowlove.profileservice.exception.security.PermissionDeniedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotUniqueException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotUnique(UsernameNotUniqueException ex) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        HttpStatus.CONFLICT.value(),
                        "Username unique conflict",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(ProfileValidationException.class)
    public ResponseEntity<ErrorResponse> handleProfileValidation(ProfileValidationException ex) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        "Profile validation conflict",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(ProfileNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProfileNotFound(ProfileNotFoundException ex) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        "Profile not found",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    // TODO: сделать корректную обработку исключений некорректного UUID
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIncorrectUUID(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        "Incorrect UUID",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(MissingAuthHeadersException.class)
    public ResponseEntity<ErrorResponse> handleMissingAuthHeaders(MissingAuthHeadersException ex) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        HttpStatus.UNAUTHORIZED.value(),
                        "Missing Auth Headers",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationCredentialsNotFound(AuthenticationCredentialsNotFoundException ex) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        HttpStatus.UNAUTHORIZED.value(),
                        "Authentication Credentials not found",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(PermissionDeniedException.class)
    public ResponseEntity<ErrorResponse> handlePermissionDenied(PermissionDeniedException ex) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        HttpStatus.UNAUTHORIZED.value(),
                        "Permission denied",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(UserIDNotUniqueException.class)
    public ResponseEntity<ErrorResponse> handleUserIDNotUnique(UserIDNotUniqueException ex) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        HttpStatus.CONFLICT.value(),
                        "UserId unique conflict",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        "Incorrect JSON format",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

}
