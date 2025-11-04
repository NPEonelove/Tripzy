package com.meowlove.apigateway.filter;

import com.meowlove.apigateway.exception.JwtValidationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    @Value("${jwt.secret-key}")
    private String jwtSecretKey;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().toString();
        String method = request.getMethod().name();

        log.debug("Request handled. Path: {}. Method: {}", path, method);

        if (path.startsWith("/api/v1/auth")) {
            log.debug("Skip JWT validation for auth endpoint: {}", path);
            return chain.filter(exchange);
        }

        String accessToken = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (StringUtils.hasText(accessToken) && accessToken.startsWith("Bearer ")) {
            accessToken = accessToken.substring(7);
            validateJwtToken(accessToken);
            log.debug("JWT token successfully validated. Path: {}. Method: {}", path, method);
        } else {
            log.warn("Missing or invalid Authorization header - expected 'Bearer <token>'. Path: {}. Method: {}", path, method);
            throw new JwtException("Incorrect token");
        }

        String userId = getUserIdFromJwtToken(accessToken);
        String userRole = getUserRoleFromJwtToken(accessToken);

        log.info("User successfully logged in. Path: {}. Method: {}. UserId: {}. Role: {}", path, method, userId, userRole);

        ServerHttpRequest modifiedRequest = request.mutate()
                .header("X-User-Id", userId)
                .header("X-User-Role", "ROLE_" +
                        userRole)
                .build();

        log.debug("Modifying request headers. Path: {}. Method: {}", path, method);

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
