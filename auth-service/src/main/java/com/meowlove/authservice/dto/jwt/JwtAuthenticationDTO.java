package com.meowlove.authservice.dto.jwt;

import lombok.Data;

@Data
public class JwtAuthenticationDTO {

    private String accessToken;
    private String refreshToken;

}
