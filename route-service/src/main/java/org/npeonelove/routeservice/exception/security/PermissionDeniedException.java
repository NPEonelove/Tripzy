package org.npeonelove.routeservice.exception.security;

public class PermissionDeniedException extends RuntimeException {
    public PermissionDeniedException(String message) {
        super(message);
    }
}
