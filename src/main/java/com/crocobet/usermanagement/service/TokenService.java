package com.crocobet.usermanagement.service;

import com.crocobet.usermanagement.config.properties.JwtProperties;
import com.crocobet.usermanagement.util.constants.CustomJwtClaims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final JwtEncoder encoder;
    private final JwtProperties jwtProperties;

    public String generateAccessToken(String subject, List<String> roles) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(now.plus(jwtProperties.expirationInMinutes(), ChronoUnit.MINUTES))
                .issuer(jwtProperties.issuer())
                .audience(List.of(jwtProperties.audience()))
                .subject(subject)
                .claim(CustomJwtClaims.ROLES.name().toLowerCase(), String.join(" ", roles))
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

}