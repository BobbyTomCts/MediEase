package com.backend.mediassist.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    
    // Secret key for JWT (in production, use environment variable)
    private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    
    // Token valid for 24 hours
    private final long EXPIRATION_TIME = 24 * 60 * 60 * 1000; // 24 hours in milliseconds
    
    // Generate JWT token
    public String generateToken(String email, Long userId, String role) {
        return Jwts.builder()
                .setSubject(email) // Email as subject
                .claim("userId", userId) // User ID
                .claim("role", role) // Role (EMPLOYEE or ADMIN)
                .setIssuedAt(new Date()) // When token was created
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Expiry time
                .signWith(SECRET_KEY) // Sign with secret key
                .compact();
    }
    
    // Get email from token
    public String getEmailFromToken(String token) {
        return getClaims(token).getSubject();
    }
    
    // Get user ID from token
    public Long getUserIdFromToken(String token) {
        return getClaims(token).get("userId", Long.class);
    }
    
    // Get role from token
    public String getRoleFromToken(String token) {
        return getClaims(token).get("role", String.class);
    }
    
    // Check if token is expired
    public boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }
    
    // Validate token
    public boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }
    
    // Get all claims from token
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
