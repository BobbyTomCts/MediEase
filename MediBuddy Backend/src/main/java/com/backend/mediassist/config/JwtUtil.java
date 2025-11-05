package com.backend.mediassist.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Key;
import java.util.Date;

/**
 * Single JWT Authentication Component
 * - Generates JWT tokens for login
 * - Validates JWT tokens for all requests
 * - Automatically protects all endpoints except login and register
 */
@Component
public class JwtUtil extends OncePerRequestFilter {
    
    // Secret key for JWT
    private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    
    // Token valid for 24 hours
    private final long EXPIRATION_TIME = 24 * 60 * 60 * 1000;
    
    // ==================== TOKEN GENERATION ====================
    
    public String generateToken(String email, Long userId, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("userId", userId)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }
    
    // ==================== TOKEN VALIDATION (Filter) ====================
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String path = request.getRequestURI();
        
        // Skip JWT validation for login and register endpoints
        if (path.contains("/login") || path.contains("/register")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        // Get token from Authorization header
        String authHeader = request.getHeader("Authorization");
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            sendUnauthorizedResponse(response, "Missing or invalid Authorization header");
            return;
        }
        
        String token = authHeader.substring(7);
        
        try {
            // Validate token and extract user info
            if (validateToken(token)) {
                // Store user info in request attributes for controllers to use
                request.setAttribute("userId", getUserIdFromToken(token));
                request.setAttribute("userEmail", getEmailFromToken(token));
                request.setAttribute("userRole", getRoleFromToken(token));
                
                // Continue to controller
                filterChain.doFilter(request, response);
            } else {
                sendUnauthorizedResponse(response, "Token expired or invalid");
            }
        } catch (Exception e) {
            sendUnauthorizedResponse(response, "Invalid token: " + e.getMessage());
        }
    }
    
    private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\":\"Unauthorized\",\"message\":\"" + message + "\"}");
    }
    
    // ==================== TOKEN UTILITY METHODS ====================
    
    public String getEmailFromToken(String token) {
        return getClaims(token).getSubject();
    }
    
    public Long getUserIdFromToken(String token) {
        return getClaims(token).get("userId", Long.class);
    }
    
    public String getRoleFromToken(String token) {
        return getClaims(token).get("role", String.class);
    }
    
    public boolean validateToken(String token) {
        try {
            return !getClaims(token).getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
    
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
