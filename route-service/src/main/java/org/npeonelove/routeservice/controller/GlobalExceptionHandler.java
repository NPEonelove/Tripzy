package org.npeonelove.routeservice.controller;

import org.npeonelove.routeservice.exception.ErrorResponse;
import org.npeonelove.routeservice.exception.route.RouteValidationException;
import org.npeonelove.routeservice.exception.route.RouteNotFoundException;
import org.npeonelove.routeservice.exception.security.PermissionDeniedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    private final HttpStatus routeValidationStatus = HttpStatus.BAD_REQUEST;
    private final HttpStatus routeNotFoundStatus = HttpStatus.NOT_FOUND;
    private final HttpStatus permissionDeniedStatus = HttpStatus.UNAUTHORIZED;

    @ExceptionHandler(RouteValidationException.class)
    public ResponseEntity<ErrorResponse> handleRouteValidation(RouteValidationException ex) {
        return ResponseEntity.status(routeValidationStatus).body(
                new ErrorResponse(
                        routeValidationStatus.value(),
                        "Route validation conflict",
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

    @ExceptionHandler(RouteNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotUnique(RouteNotFoundException ex) {
        return ResponseEntity.status(routeNotFoundStatus).body(
                new ErrorResponse(
                        routeNotFoundStatus.value(),
                        "Route not found",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }
    
}
