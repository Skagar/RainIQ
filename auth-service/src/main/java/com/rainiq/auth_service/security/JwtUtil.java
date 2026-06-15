package com.rainiq.auth_service.security;

import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private long expiration;
    private SecretKey getSigningKey()
    {
        byte[] keyBytes= Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String email,String role)
    {
        return Jwts.builder().
                subject(email).
                issuedAt(new Date())
                .expiration(new Date(new Date().getTime()+expiration)).signWith(getSigningKey())
                .claim("role",role)
                .compact();

    }

    public String extractEmail(String token)
    {
         return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public Date extractExpiration(String token)
    {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload().getExpiration();
    }
    public Boolean isTokenValid(String token)
    {
        try {
            String email = extractEmail(token);
            Date expiry = extractExpiration(token);
            return new Date().before(expiry);
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
