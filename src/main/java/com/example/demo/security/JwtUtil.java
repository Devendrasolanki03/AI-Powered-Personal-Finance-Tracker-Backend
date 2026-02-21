package com.example.demo.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.example.demo.entity.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private static final String SECRET =
            "finance_secret_key_which_is_long_enough_256bit_123456";

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    // ---------------- GENERATE TOKEN ----------------
    public String generateToken(String email, Role role) {

        return Jwts.builder()
                .setSubject(email)
                .claim("role", role.name()) // USER / ADMIN
                .setIssuedAt(new Date())
                .setExpiration(
                    new Date(System.currentTimeMillis() + 60 * 60 * 1000)
                )
                .signWith(key)
                .compact();
    }

    // ---------------- EXTRACT CLAIMS ----------------
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return extractClaims(token).get("role", String.class);
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token)
                .getExpiration()
                .before(new Date());
    }
}
