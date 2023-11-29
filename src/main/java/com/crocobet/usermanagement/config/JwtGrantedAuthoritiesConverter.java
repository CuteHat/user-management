package com.crocobet.usermanagement.config;

import com.crocobet.usermanagement.util.constants.CustomJwtClaims;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class JwtGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        List<String> roles = new ArrayList<>();

        Object roleClaim = jwt.getClaims().get(CustomJwtClaims.ROLES.name().toLowerCase());
        if (roleClaim instanceof String) {
            roles.add((String) roleClaim);
        } else if (roleClaim instanceof List) {
            roles = (List<String>) roleClaim;
        }

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }
}
