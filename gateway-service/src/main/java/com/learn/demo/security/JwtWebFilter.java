package com.learn.demo.security;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtWebFilter implements WebFilter {

    private final JwtValidator jwtValidator;

    @Override
    @NonNull
    public Mono<Void> filter(
            @NonNull ServerWebExchange exchange,
            @NonNull WebFilterChain chain
    ) {
        final String path = exchange.getRequest().getURI().getPath();

        if (path.startsWith("/auth") || path.startsWith("/actuator")) {
            return chain.filter(exchange);
        }

        String header = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        final String token = header.substring(7);
        try {
            Claims claims = this.jwtValidator.validateAndGetClaims(token);
            String username = claims.getSubject();

            @SuppressWarnings("unchecked")
            List<String> roles = claims.get("roles", List.class);

            var authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();
            var auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
            var context = new SecurityContextImpl(auth);

            return chain.filter(exchange)
                    .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(context)));
        } catch (Exception ex) {
            log.warn("Exception occurred while verifying token: {}", ex.getMessage(), ex);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }
}
