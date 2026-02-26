package com.learn.demo.security;

import org.jspecify.annotations.NonNull;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            @NonNull ServerHttpSecurity httpSecurity,
            JwtWebFilter jwtWebFilter
    ) {
        httpSecurity
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange ->
                        exchange
                                .pathMatchers(
                                        "/auth/**",
                                        "/actuator/**"
                                )
                                .permitAll()
                                .anyExchange()
                                .authenticated()
                )
                .addFilterBefore(jwtWebFilter, SecurityWebFiltersOrder.AUTHENTICATION);
        return httpSecurity.build();
    }
}
