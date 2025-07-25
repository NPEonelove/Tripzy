package com.meowlove.authservice.controller;

import com.meowlove.authservice.dto.jwt.JwtAuthenticationDTO;
import com.meowlove.authservice.dto.jwt.RefreshTokenDTO;
import com.meowlove.authservice.dto.user.ChangePasswordRequestDTO;
import com.meowlove.authservice.dto.user.ChangePasswordResponseDTO;
import com.meowlove.authservice.dto.user.UserCredentialsRequestDTO;
import com.meowlove.authservice.exception.JwtValidationException;
import com.meowlove.authservice.exception.PasswordChangeException;
import com.meowlove.authservice.exception.UserValidationException;
import com.meowlove.authservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication API", description = "API для аутентификации и управления учетными данными")
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Регистрация нового пользователя"
    )
    @PostMapping("/sign-up")
    public ResponseEntity<JwtAuthenticationDTO> signUp(@RequestBody @Valid UserCredentialsRequestDTO userCredentialsRequestDTO,
                                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new UserValidationException(validateBindingResult(bindingResult).toString());
        }

        return ResponseEntity.ok(authService.signUp(userCredentialsRequestDTO));
    }

    @Operation(
            summary = "Авторизация пользователя"
    )
    @PostMapping("/sign-in")
    public ResponseEntity<JwtAuthenticationDTO> signIn(@RequestBody @Valid UserCredentialsRequestDTO userCredentialsRequestDTO,
                                                       BindingResult bindingResult) throws AuthenticationException {
        if (bindingResult.hasErrors()) {
            throw new UserValidationException(validateBindingResult(bindingResult).toString());
        }

        return ResponseEntity.ok(authService.signIn(userCredentialsRequestDTO));
    }

    @Operation(
            summary = "Выход из аккаунта пользователя",
            description = "Требует авторизованного запроса"
    )
    @PostMapping("/sign-out")
    public ResponseEntity<Boolean> signOut(@RequestBody RefreshTokenDTO refreshTokenDTO) {
        return ResponseEntity.ok(authService.signOut(refreshTokenDTO));
    }

    @Operation(
            summary = "Обновление access токена по refresh токену"
    )
    @PostMapping("/refresh-access-token")
    public ResponseEntity<JwtAuthenticationDTO> refreshAccessToken(@RequestBody @Valid RefreshTokenDTO refreshTokenDTO,
                                                                   BindingResult bindingResult) throws AuthenticationException {
        if (bindingResult.hasErrors()) {
            throw new JwtValidationException(validateBindingResult(bindingResult).toString());
        }

        return ResponseEntity.ok(authService.refreshAccessToken(refreshTokenDTO));
    }

    @Operation(
            summary = "Смена пароля пользователя",
            description = "Требует авторизованного запроса"
    )
    @PatchMapping("/change-password")
    public ResponseEntity<ChangePasswordResponseDTO> changePassword(@RequestBody @Valid ChangePasswordRequestDTO changePasswordRequestDTO,
                                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new PasswordChangeException(validateBindingResult(bindingResult).toString());
        }

        return ResponseEntity.ok(authService.changePassword(changePasswordRequestDTO));
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
