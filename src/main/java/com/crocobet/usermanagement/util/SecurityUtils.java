package com.crocobet.usermanagement.util;

import com.nimbusds.jwt.JWTClaimNames;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public class SecurityUtils {
    public static Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Jwt jwtCredentials = (Jwt) authentication.getCredentials();
            return Long.valueOf(jwtCredentials.getClaim(JWTClaimNames.SUBJECT));
        }

        return null;
    }
}