package com.crocobet.usermanagement.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "security.jwt")
public record JwtProperties(String issuer,
                     String audience,
                     Long expirationInMinutes,
                     RSAPrivateKey privateKey,
                     RSAPublicKey publicKey) {
}