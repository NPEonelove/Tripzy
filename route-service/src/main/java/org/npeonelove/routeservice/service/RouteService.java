package org.npeonelove.routeservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.npeonelove.routeservice.dto.point.CreatePointRequestDTO;
import org.npeonelove.routeservice.dto.point.CreatePointResponseDTO;
import org.npeonelove.routeservice.dto.route.*;
import org.npeonelove.routeservice.exception.route.RouteNotFoundException;
import org.npeonelove.routeservice.exception.security.PermissionDeniedException;
import org.npeonelove.routeservice.model.Point;
import org.npeonelove.routeservice.model.Route;
import org.npeonelove.routeservice.repository.RouteRepository;
import org.npeonelove.routeservice.security.SecurityService;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RouteService {

    private final RouteRepository routeRepository;
    private final ModelMapper modelMapper;
    private final SecurityService securityService;

    // получение маршрута
    public GetRouteResponseDTO getRoute(String routeId) {
        return modelMapper.map(findRoute(routeId), GetRouteResponseDTO.class);
    }

    // создание нового маршрута
    @Transactional
    public CreateRouteResponseDTO createRoute(CreateRouteRequestDTO createRouteRequestDTO) {
        Route route = modelMapper.map(createRouteRequestDTO, Route.class);

        route.setUserId(securityService.getUUIDFromSecurityContext().toString());

        return modelMapper.map(routeRepository.save(route), CreateRouteResponseDTO.class);
    }

    // обновление данных маршрута
    @Transactional
    public UpdateRouteResponseDTO updateRoute(String routeId, UpdateRouteRequestDTO updateRouteRequestDTO) {
         validateOwner(routeId);

         Route route = findRoute(routeId);

         route.setCategory(updateRouteRequestDTO.getCategory());
         route.setCountry(updateRouteRequestDTO.getCountry());
         route.setTitle(updateRouteRequestDTO.getTitle());
         route.setDescription(updateRouteRequestDTO.getDescription());

         routeRepository.save(route);

         return modelMapper.map(route, UpdateRouteResponseDTO.class);
    }

    // удаление маршрута
    @Transactional
    public String deleteRoute(String routeId) {
        validateOwner(routeId);

        if (routeRepository.findRouteById(routeId).isPresent()) {
            routeRepository.deleteById(routeId);
            return "Route with id " + routeId + " has been deleted";
        } else {
            throw new RouteNotFoundException(routeId);
        }
    }

    // добавление новых точек
    @Transactional
    public CreatePointResponseDTO addPoint(String routeId, CreatePointRequestDTO createPointRequestDTO) {

        log.info(createPointRequestDTO.toString());

        Route route = findRoute(routeId);

        Point point = new Point();

        point.setTitle(createPointRequestDTO.getTitle());
        point.setDescription(createPointRequestDTO.getDescription());
        point.setCoordinates(new GeoJsonPoint(createPointRequestDTO.getCoordinates()[0], createPointRequestDTO.getCoordinates()[1]));
        point.setCoordinates(new GeoJsonPoint(createPointRequestDTO.getCoordinates()[0], createPointRequestDTO.getCoordinates()[1]));
        point.setCoordinates(new GeoJsonPoint(createPointRequestDTO.getCoordinates()[0], createPointRequestDTO.getCoordinates()[1]));

        route.addPoint(point);

        return modelMapper.map(routeRepository.save(route), CreatePointResponseDTO.class);
    }

    // проверка владельца
    private void validateOwner(String routeId) {
        Route route = routeRepository.findRouteById(routeId).orElseThrow(() ->
                new RouteNotFoundException("Route with id " + routeId + " not found"));

        if (route.getUserId().equals(securityService.getUUIDFromSecurityContext().toString())) {
            return;
        }

        throw new PermissionDeniedException("Permission denied");
    }

    // получение маршрута по id
    private Route findRoute(String routeId) {
        return routeRepository.findRouteById(routeId).orElseThrow(() -> new RouteNotFoundException(routeId));
    }

}
