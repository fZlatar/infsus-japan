package hr.fer.infsus.japan.services.impl;

import hr.fer.infsus.japan.services.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    @Value("${application.security.jwt.access.secret-key}")
    private String accessSecret;

    @Value("${application.security.jwt.refresh.secret-key}")
    private String refreshSecret;

    @Value("${application.security.jwt.access.expiration}")
    private long accessExpiration;

    @Value("${application.security.jwt.refresh.expiration}")
    private long refreshExpiration;

    @Override
    public String generateAccess(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(getSignInKey(accessSecret), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String generateRefresh(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(getSignInKey(refreshSecret), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String extractSubjectFromAccess(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey(accessSecret)) // Use accessSecret key for access token
                .build()
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();
    }

    @Override
    public String extractSubjectFromRefresh(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey(refreshSecret)) // Use accessSecret key for access token
                .build()
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();
    }

    private Key getSignInKey(String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
