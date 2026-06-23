package com.rainiq.rainfallservice.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    private SecretKey getSigningKey()
    {
        byte[] keyBytes= Decoders.BASE64.decode(secretKey);
       return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractEmail(String token)
    {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public String extractRole(String token)
    {
        return  Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload().get("role",String.class);
    }
    public Date extractExpiration(String token)
    {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload().getExpiration();
    }

    public Boolean isTokenValid(String token)
    {
        try {
            Date expiry = extractExpiration(token);
            return new Date().before(expiry);
        }
        catch (Exception e)
        {
            System.out.println("TOKEN VALIDATION FAILED: " + e.getMessage());
            return false;
        }
    }
}
