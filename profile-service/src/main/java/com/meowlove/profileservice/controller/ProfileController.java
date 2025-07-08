package com.meowlove.profileservice.controller;

import com.meowlove.profileservice.dto.profile.*;
import com.meowlove.profileservice.exception.profile.ProfilePasswordValidationException;
import com.meowlove.profileservice.exception.profile.ProfileValidationException;
import com.meowlove.profileservice.model.Profile;
import com.meowlove.profileservice.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profiles")
public class ProfileController {

    private final ProfileService profileService;

    // создание профиля (для сервиса аунтетификации)
    @PostMapping()
    public ResponseEntity<CreateProfileResponseDTO> createProfile(@RequestBody @Valid CreateProfileRequestDTO createProfileRequestDTO,
                                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ProfileValidationException(validateBindingResult(bindingResult).toString());
        }

        return ResponseEntity.ok(profileService.createProfile(createProfileRequestDTO));
    }

    // редактирование пароля (для сервиса аутентификации)
    @PatchMapping("/{uuid}/credentials")
    public ResponseEntity<UpdateProfilePasswordResponseDTO> updateProfilePassword(@PathVariable("uuid") String uuid,
                                                                                  @RequestBody @Valid UpdateProfilePasswordRequestDTO updateProfilePasswordRequestDTO,
                                                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ProfilePasswordValidationException(validateBindingResult(bindingResult).toString());
        }

        return ResponseEntity.ok(profileService.updateProfilePassword(UUID.fromString(uuid), updateProfilePasswordRequestDTO));
    }

    // получение профиля для страницы профиля
    @GetMapping("/{uuid}")
    public ResponseEntity<GetProfileOverviewResponseDTO> getProfile(@PathVariable("uuid") String uuid) {
        return ResponseEntity.ok(profileService.getProfileOverview(UUID.fromString(uuid)));
    }

    // редактирование профиля (без credentials)
    @PatchMapping("/{uuid}")
    public ResponseEntity<UpdateProfileResponseDTO> updateProfile(@PathVariable("uuid") String uuid,
                                                                  @RequestBody @Valid UpdateProfileRequestDTO updateProfileRequestDTO,
                                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ProfileValidationException(validateBindingResult(bindingResult).toString());
        }

        return ResponseEntity.ok(profileService.updateProfile(UUID.fromString(uuid), updateProfileRequestDTO));
    }

    // удаление профиля
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
