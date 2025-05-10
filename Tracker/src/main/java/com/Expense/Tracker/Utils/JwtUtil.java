package com.Expense.Tracker.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
//private final Key key=Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
  //  private String secretKey = "VGVzdFNlY3JldEtleUZvckpXVFRva2VuMTIzIQ==";
private String key="qf6QkVJxv28Mp5LCvA7zg4Pq9JwXzq5WzK9mWdBVu2GPH3wNwLqVKdHJUZ9qx4aG";
    // Method to generate a JWT token
    public String generateToken(String emailId) {
        return Jwts.builder()
                .setSubject(emailId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours expiration
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }
    public boolean validateToken(String token, String emailId) {
        String extractedEmailId = extractEmailId(token);
        return (extractedEmailId.equals(emailId) && !isTokenExpired(token));
    }
    public String extractEmailId(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public boolean isTokenExpired(String token) {
        Date expirationDate = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expirationDate.before(new Date());
    }
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
