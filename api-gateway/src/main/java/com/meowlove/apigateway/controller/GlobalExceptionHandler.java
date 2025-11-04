package com.meowlove.apigateway.controller;

import com.meowlove.apigateway.exception.ErrorResponse;
import com.meowlove.apigateway.exception.JwtValidationException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private final HttpStatus expiredJwtStatus = HttpStatus.FORBIDDEN;
    private final HttpStatus jwtValidationStatus = HttpStatus.FORBIDDEN;

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleExpiredJwt(ExpiredJwtException ex) {
        log.warn("Jwt token expired {}", ex.getMessage());
        return ResponseEntity.status(expiredJwtStatus).body(
                new ErrorResponse(
                        expiredJwtStatus.value(),
                        "Jwt expired",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(JwtValidationException.class)
    public ResponseEntity<ErrorResponse> handleJwtValidation(JwtValidationException ex) {
        log.warn("Jwt token validation failed {}", ex.getMessage());
        return ResponseEntity.status(jwtValidationStatus).body(
                new ErrorResponse(
                        jwtValidationStatus.value(),
                        "Jwt validation error",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

}
