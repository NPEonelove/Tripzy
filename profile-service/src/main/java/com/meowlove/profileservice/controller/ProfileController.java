package com.meowlove.profileservice.controller;

import com.meowlove.profileservice.dto.profile.*;
import com.meowlove.profileservice.exception.profile.ProfileValidationException;
import com.meowlove.profileservice.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.StringReader;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profiles")
@Tag(name = "Profiles API", description = "API для управления данными профилей. " +
        "Все запросы требуют авторизации")
public class ProfileController {

    private final ProfileService profileService;

    @Operation(
            summary = "Создание нового профиля"
    )
    @PostMapping()
    public ResponseEntity<CreateProfileResponseDTO> createProfile(@RequestBody @Valid CreateProfileRequestDTO createProfileRequestDTO,
                                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ProfileValidationException(validateBindingResult(bindingResult).toString());
        }

        return ResponseEntity.ok(profileService.createProfile(createProfileRequestDTO));
    }

    @Operation(
            summary = "Получение данных профиля"
    )
    @GetMapping("/{uuid}")
    public ResponseEntity<GetProfileOverviewResponseDTO> getProfile(@PathVariable("uuid") String uuid) {
        return ResponseEntity.ok(profileService.getProfileOverview(UUID.fromString(uuid)));
    }

    @Operation(
            summary = "Редактирование данных профиля"
    )
    @PatchMapping("/{uuid}")
    public ResponseEntity<UpdateProfileResponseDTO> updateProfile(@PathVariable("uuid") String uuid,
                                                                  @RequestBody @Valid UpdateProfileRequestDTO updateProfileRequestDTO,
                                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ProfileValidationException(validateBindingResult(bindingResult).toString());
        }

        return ResponseEntity.ok(profileService.updateProfile(UUID.fromString(uuid), updateProfileRequestDTO));
    }

    @Operation(
            summary = "Обновление фотографии профиля"
    )
    @PatchMapping("/{uuid}/upload-image")
    public ResponseEntity<String> uploadProfileImage(@PathVariable("uuid") String uuid,
                                                     @RequestParam(value = "image", required = false) MultipartFile image) {
        return ResponseEntity.ok(profileService.uploadProfileImage(uuid, image));
    }

    @Operation(
            summary = "Удаление профиля"
    )
    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@PathVariable("uuid") String uuid) {
        profileService.deleteProfile(UUID.fromString(uuid));
    }

    // получение строки с ошибками валидации для исключений
    private StringBuilder validateBindingResult(BindingResult bindingResult) {
        StringBuilder errors = new StringBuilder();
        for (FieldError error : bindingResult.getFieldErrors()) {
            errors.append(error.getDefaultMessage());
            errors.append(" ");
        }
        return errors;
    }

}
