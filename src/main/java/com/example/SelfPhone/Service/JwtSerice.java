package com.example.SelfPhone.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class JwtSerice {

    @Value("${jwtSecret}")
    private String jwtSecret;
    private static long EXPIRATION_TIME = TimeUnit.MINUTES.toMillis(10);
    private static long REFRESH_INTERVAL = TimeUnit.DAYS.toMillis(7);

    // Tạo key giống SecurityConfig
    private Key getSigningKey() {
        return new SecretKeySpec(
                jwtSecret.getBytes(StandardCharsets.UTF_8),
                "HmacSHA512");
    }

    public String generateAccessToken(String username, String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        Key key = getSigningKey();

        String token = Jwts.builder()
                .claim("username", username)
                .claim("scope", role)
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512) // Dùng Key object
                .compact();

        return token;
    }

    public String generateRefreshToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + REFRESH_INTERVAL);

        Key key = getSigningKey();

        return Jwts.builder()
                .claim("username", username)
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512) // Dùng Key object
                .compact();
    }

    public String getUsernameInToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("username", String.class);
    }
}
