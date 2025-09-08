package org.npeonelove.routeservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.npeonelove.routeservice.dto.route.CreateRouteRequestDTO;
import org.npeonelove.routeservice.dto.route.CreateRouteResponseDTO;
import org.npeonelove.routeservice.exception.route.CreateRouteException;
import org.npeonelove.routeservice.service.RouteService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @PostMapping()
    public ResponseEntity<CreateRouteResponseDTO> createRoute(@RequestBody @Valid CreateRouteRequestDTO createRouteRequestDTO,
                                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CreateRouteException(validateBindingResult(bindingResult));
        }

        return ResponseEntity.ok(routeService.createRoute(createRouteRequestDTO));
    }

    // получение строки с ошибками валидации для исключений
    private String validateBindingResult(BindingResult bindingResult) {
        StringBuilder errors = new StringBuilder();
        for (FieldError error : bindingResult.getFieldErrors()) {
            errors.append(error.getDefaultMessage());
            errors.append(" ");
        }
        return errors.toString();
    }
}
