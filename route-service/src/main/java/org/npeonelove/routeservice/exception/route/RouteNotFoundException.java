package org.npeonelove.routeservice.exception.route;

public class RouteNotFoundException extends RuntimeException {
    public RouteNotFoundException(String message) {
        super(message);
    }
}
