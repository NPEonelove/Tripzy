package org.npeonelove.routeservice.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class GatewayHeadersAuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String userId = request.getHeader("X-User-Id");
        String role = request.getHeader("X-User-Role");

        if (userId == null || role == null) {
            filterChain.doFilter(request, response);
            return;
//            throw new MissingAuthHeadersException("Missing X-User-Id or X-User-Role headers");
        }

        UsernamePasswordAuthenticationToken authentication = createAuthentication(userId, role);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken createAuthentication(String userId, String role) {
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(role)
        );

        return new UsernamePasswordAuthenticationToken(
                userId,
                null,
                authorities
        );
    }
}