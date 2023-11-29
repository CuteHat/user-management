package com.crocobet.usermanagement.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertNotNull;

@SpringBootTest
class TokenServiceTest {
    @Autowired
    private TokenService tokenService;

    @Test
    public void testGenerateAccessToken() {
        String token = tokenService.generateAccessToken("123", List.of("ROLE_USER"));

        assertNotNull(token, "Token should not be null");
    }
}