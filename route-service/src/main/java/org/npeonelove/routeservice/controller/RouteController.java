package org.npeonelove.routeservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.npeonelove.routeservice.dto.point.CreatePointRequestDTO;
import org.npeonelove.routeservice.dto.point.CreatePointResponseDTO;
import org.npeonelove.routeservice.dto.route.*;
import org.npeonelove.routeservice.exception.route.RouteValidationException;
import org.npeonelove.routeservice.service.RouteService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    // получение маршрута
    @GetMapping("/{uuid}")
    public ResponseEntity<GetRouteResponseDTO> getRoute(@PathVariable("uuid") String uuid) {
        return ResponseEntity.ok(routeService.getRoute(uuid));
    }

    // создание маршрута
    @PostMapping()
    public ResponseEntity<CreateRouteResponseDTO> createRoute(@RequestBody @Valid CreateRouteRequestDTO createRouteRequestDTO,
                                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RouteValidationException(validateBindingResult(bindingResult));
        }

        return ResponseEntity.ok(routeService.createRoute(createRouteRequestDTO));
    }

    // обновление данных маршрута
    @PatchMapping("/{uuid}")
    public ResponseEntity<UpdateRouteResponseDTO> updateRoute(@PathVariable("uuid") String uuid,
                                                              @RequestBody @Valid UpdateRouteRequestDTO updateRouteRequestDTO,
                                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RouteValidationException(validateBindingResult(bindingResult));
        }

        return ResponseEntity.ok(routeService.updateRoute(uuid, updateRouteRequestDTO));
    }

    // удаление маршрута
    @DeleteMapping("/{uuid}")
    public ResponseEntity<String> deleteRoute(@PathVariable("uuid") String uuid) {
        return ResponseEntity.ok(routeService.deleteRoute(uuid));
    }

    // добавление точки
    @PostMapping("/{uuid}")
    public ResponseEntity<CreatePointResponseDTO> createPoint(@PathVariable("uuid") String uuid,
                                                              @RequestBody @Valid CreatePointRequestDTO createPointRequestDTO,
                                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RouteValidationException(validateBindingResult(bindingResult));
        }

        return ResponseEntity.ok(routeService.addPoint(uuid, createPointRequestDTO));
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
