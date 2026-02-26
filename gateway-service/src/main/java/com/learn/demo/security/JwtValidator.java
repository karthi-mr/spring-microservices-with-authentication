package com.learn.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
@RequiredArgsConstructor
public class JwtValidator {

    private final JwtProperties jwtProperties;

    @NonNull
    private SecretKey signinKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.secretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims validateAndGetClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(signinKey())
                .requireIssuer(jwtProperties.issuer())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
