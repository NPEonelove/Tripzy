package org.npeonelove.routeservice.repository;

import org.npeonelove.routeservice.model.Route;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RouteRepository extends MongoRepository<Route, String> {

    Optional<Route> findRouteById(String id);
}
