package com.cybernetics.user_managment_ms.service.impl;

import com.cybernetics.user_managment_ms.config.JwtProperties;
import com.cybernetics.user_managment_ms.entity.RefreshTokenEntity;
import com.cybernetics.user_managment_ms.entity.UserEntity;
import com.cybernetics.user_managment_ms.repository.RefreshTokenRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtService {

    private final JwtProperties jwtProperties;
    private  Key signingKey;
    private final RefreshTokenRepository refreshTokenRepository;

    @PostConstruct
    void check() {
        System.err.println("jwtProperties"+ jwtProperties);
    }

    public JwtService(JwtProperties jwtProperties,
                      RefreshTokenRepository refreshTokenRepository) {
        this.jwtProperties = jwtProperties;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @PostConstruct
    void init() {
        this.signingKey = Keys.hmacShaKeyFor(
                jwtProperties.accessToken().secret().getBytes()
        );
    }

    public String generateAccessToken(UserEntity userEntity) {

        Instant now = new Date().toInstant();
        Instant expiry =
                now.plus(jwtProperties.accessToken()
                                .expirationMinutes(),
                        ChronoUnit.MINUTES);


        return Jwts.builder()
                .setSubject(userEntity.getId())
                .claim("userName", userEntity.getUserName())
                .claim("role", userEntity.getRole())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiry))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }


    public RefreshTokenEntity createRefreshToken(UserEntity user) {
        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime expiresAt = now.plus(
                jwtProperties.refreshToken().expirationDays(),
                ChronoUnit.DAYS
        );

        String tokenValue = generateSecureRandomToken(64);

        RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
                .user(user)
                .token(tokenValue)
                .expiresAt(expiresAt)
                .revoked(false)
                .createdAt(now)
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    private String generateSecureRandomToken(int length) {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        for (int i = 0; i < length; i++) {
            int idx = secureRandom.nextInt(chars.length());
            sb.append(chars.charAt(idx));
        }
        return sb.toString();
    }
}
