package com.meowlove.apigateway.filter;

import com.meowlove.apigateway.exception.JwtValidationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;

@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    @Value("${jwt.secret-key}")
    private String jwtSecretKey;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();

        if (request.getPath().toString().startsWith("/api/v1/auth")) {
            return chain.filter(exchange);
        }

        String accessToken = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (StringUtils.hasText(accessToken) && accessToken.startsWith("Bearer ")) {
            accessToken = accessToken.substring(7);
            validateJwtToken(accessToken);
        } else {
            throw new JwtException("Incorrect token");
        }

        ServerHttpRequest modifiedRequest = request.mutate()
                .header("X-User-Id", getUserIdFromJwtToken(accessToken))
                .header("X-User-Role", "ROLE_" +
                        getUserRoleFromJwtToken(accessToken))
                .build();

        return chain.filter(exchange.mutate().request(modifiedRequest).build());
    }

    @Override
    public int getOrder() {
        return -1;
    }

    // получение userId из access токена
    public String getUserIdFromJwtToken(String accessToken) {
        Claims claims = getClaimsFromAccessToken(accessToken);
        return claims.getSubject();
    }

    // получение роли из access токена
    public String getUserRoleFromJwtToken(String accessToken) {
        Claims claims = getClaimsFromAccessToken(accessToken);
        return claims.get("role", String.class);
    }

    // валидация jwt токена
    public void validateJwtToken(String token) {
        try {
            getClaimsFromAccessToken(token);
        } catch (Exception e) {
            throw new JwtValidationException("Invalid JWT token");
        }
    }

    // получение payload из access токена
    private Claims getClaimsFromAccessToken(String accessToken) {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseClaimsJws(accessToken)
                .getPayload();
    }

    // генерация подписи
    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
