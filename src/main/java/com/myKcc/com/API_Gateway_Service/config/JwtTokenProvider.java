package com.myKcc.com.API_Gateway_Service.config;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {


    // Use a key of appropriate length (e.g., 256 bits for HS256)
    private final SecretKey secretKey = Keys.hmacShaKeyFor("dgjorufhrsnjdu438fkdj38fdmcv7dm3ckvhrsnjjuwelueivhe848fhedldh5ndk".getBytes());

    // Method to validate the token
    public boolean validateToken(String token) {
        try {
            Claims claims = getClaims(token);

            // Check if the token is expired
            if (claims.getExpiration().before(new Date())) {
                return false; // Token is expired
            }

            return true; // Token is valid
        } catch (JwtException | IllegalArgumentException e) {
            // Log the exception for debugging (optional)
            e.printStackTrace();
            return false; // Invalid token
        }
    }

    // Method to extract claims from the token
    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Method to generate a token (optional, if needed for your use case)
    public String generateToken(String username) {
        Claims claims = Jwts.claims().setSubject(username);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Set expiration time (1 day)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }
}
