package org.npeonelove.routeservice.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.npeonelove.routeservice.dto.route.CreateRouteRequestDTO;
import org.npeonelove.routeservice.dto.route.CreateRouteResponseDTO;
import org.npeonelove.routeservice.model.Route;
import org.npeonelove.routeservice.repository.RouteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RouteService {

    private final RouteRepository routeRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public CreateRouteResponseDTO createRoute(CreateRouteRequestDTO createRouteRequestDTO) {
        return modelMapper.map(routeRepository.save(modelMapper.map(createRouteRequestDTO, Route.class)), CreateRouteResponseDTO.class);
    }

}
