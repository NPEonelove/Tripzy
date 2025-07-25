package com.meowlove.apigateway.controller;

import com.meowlove.apigateway.exception.ErrorResponse;
import com.meowlove.apigateway.exception.JwtValidationException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleExpiredJwt(ExpiredJwtException ex) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        HttpStatus.UNAUTHORIZED.value(),
                        "Jwt expired",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(JwtValidationException.class)
    public ResponseEntity<ErrorResponse> handleJwtValidation(JwtValidationException ex) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        HttpStatus.UNAUTHORIZED.value(),
                        "Jwt validation error",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

}
