package org.npeonelove.routeservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.npeonelove.routeservice.dto.category.CreateCategoryRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @PostMapping
    public ResponseEntity<CreateCategoryRequestDTO> createCategory(@Valid @RequestBody CreateCategoryRequestDTO createCategoryRequestDTO,
                                                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(validateBindingResult(bindingResult));
        }


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
