package com.myKcc.com.API_Gateway_Service.config;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtTokenProvider {

    // Use a key of appropriate length (e.g., 256 bits for HS256)
    private final SecretKey secretKey = Keys.hmacShaKeyFor("dgjorufhrsnjdu438fkdj38fdmcv7dm3ckvhrsnjjuwelueivhe848fhedldh5ndk".getBytes()); // Replace with your actual secret key

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
