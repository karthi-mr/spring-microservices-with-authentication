package com.learn.demo.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.sql.Date;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;

    @NonNull
    private SecretKey signinKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.jwtProperties.secret());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String username, List<String> roles) {
        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(this.jwtProperties.expirationMinutes() * 60L);

        return Jwts.builder()
                .issuer(jwtProperties.issuer())
                .subject(username)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .claim("roles", roles)
                .signWith(signinKey(), Jwts.SIG.HS256)
                .compact();
    }
}
