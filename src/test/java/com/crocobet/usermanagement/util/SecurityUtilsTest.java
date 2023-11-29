package com.crocobet.usermanagement.util;

import com.nimbusds.jwt.JWTClaimNames;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

class SecurityUtilsTest {

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private Jwt jwt;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void getAuthenticatedUserIdWhenAuthenticationIsNull() {
        when(securityContext.getAuthentication()).thenReturn(null);
        assertNull(SecurityUtils.getAuthenticatedUserId());
    }

    @Test
    void getAuthenticatedUserIdWhenNotAuthenticated() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);
        assertNull(SecurityUtils.getAuthenticatedUserId());
    }

    @Test
    void getAuthenticatedUserIdWhenAuthenticated() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getCredentials()).thenReturn(jwt);
        when(jwt.getClaim(JWTClaimNames.SUBJECT)).thenReturn("12345");

        Long expectedUserId = 12345L;
        assertEquals(expectedUserId, SecurityUtils.getAuthenticatedUserId());
    }
}
