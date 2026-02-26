package com.learn.demo.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.security.jwt")
public record JwtProperties(
        String secretKey,

        String issuer
) {
}
