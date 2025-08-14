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
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final HttpStatus usernameNotUniqueStatus = HttpStatus.CONFLICT;
    private final HttpStatus profileValidationStatus = HttpStatus.BAD_REQUEST;
    private final HttpStatus profileNotFoundStatus = HttpStatus.NOT_FOUND;
    private final HttpStatus incorrectUUIDStatus = HttpStatus.BAD_REQUEST;
    private final HttpStatus missingAuthHeadersStatus = HttpStatus.UNAUTHORIZED;
    private final HttpStatus authCredentialsNotFoundStatus = HttpStatus.UNAUTHORIZED;
    private final HttpStatus permissionDeniedStatus = HttpStatus.UNAUTHORIZED;
    private final HttpStatus userIDNotUniqueStatus = HttpStatus.CONFLICT;
    private final HttpStatus httpMessageNotReadableStatus = HttpStatus.BAD_REQUEST;
    private final HttpStatus httpMediaTypeNotSupportedStatus = HttpStatus.UNSUPPORTED_MEDIA_TYPE;

    @ExceptionHandler(UsernameNotUniqueException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotUnique(UsernameNotUniqueException ex) {
        return ResponseEntity.status(usernameNotUniqueStatus).body(
                new ErrorResponse(
                        usernameNotUniqueStatus.value(),
                        "Username unique conflict",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(ProfileValidationException.class)
    public ResponseEntity<ErrorResponse> handleProfileValidation(ProfileValidationException ex) {
        return ResponseEntity.status(profileValidationStatus).body(
                new ErrorResponse(
                        profileValidationStatus.value(),
                        "Profile validation conflict",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(ProfileNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProfileNotFound(ProfileNotFoundException ex) {
        return ResponseEntity.status(profileNotFoundStatus).body(
                new ErrorResponse(
                        profileNotFoundStatus.value(),
                        "Profile not found",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIncorrectUUID(IllegalArgumentException ex) {
        return ResponseEntity.status(incorrectUUIDStatus).body(
                new ErrorResponse(
                        incorrectUUIDStatus.value(),
                        "Incorrect UUID",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(MissingAuthHeadersException.class)
    public ResponseEntity<ErrorResponse> handleMissingAuthHeaders(MissingAuthHeadersException ex) {
        return ResponseEntity.status(missingAuthHeadersStatus).body(
                new ErrorResponse(
                        missingAuthHeadersStatus.value(),
                        "Missing Auth Headers",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationCredentialsNotFound(AuthenticationCredentialsNotFoundException ex) {
        return ResponseEntity.status(authCredentialsNotFoundStatus).body(
                new ErrorResponse(
                        authCredentialsNotFoundStatus.value(),
                        "Authentication Credentials not found",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(PermissionDeniedException.class)
    public ResponseEntity<ErrorResponse> handlePermissionDenied(PermissionDeniedException ex) {
        return ResponseEntity.status(permissionDeniedStatus).body(
                new ErrorResponse(
                        permissionDeniedStatus.value(),
                        "Permission denied",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(UserIDNotUniqueException.class)
    public ResponseEntity<ErrorResponse> handleUserIDNotUnique(UserIDNotUniqueException ex) {
        return ResponseEntity.status(userIDNotUniqueStatus).body(
                new ErrorResponse(
                        userIDNotUniqueStatus.value(),
                        "UserId unique conflict",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(httpMessageNotReadableStatus).body(
                new ErrorResponse(
                        httpMessageNotReadableStatus.value(),
                        "Incorrect JSON format",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
        return ResponseEntity.status(httpMediaTypeNotSupportedStatus).body(
                new ErrorResponse(
                        httpMediaTypeNotSupportedStatus.value(),
                        "Unsupported Media Type",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }
}
