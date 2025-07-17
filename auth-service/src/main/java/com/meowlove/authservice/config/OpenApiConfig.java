package com.meowlove.authservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Auth system API",
                description = "API системы аутентификации",
                version = "1.0.0",
                contact = @Contact(
                        name = "Ivan Kochetov",
                        url = "https://t.me/NPEonelove"
                )
        )
)
public class OpenApiConfig {

}
