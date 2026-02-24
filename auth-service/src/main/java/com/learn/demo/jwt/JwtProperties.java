package com.learn.demo.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.security.jwt")
public record JwtProperties(

        CharSequence secret,

        String issuer,

        long expirationMinutes
) {

}
