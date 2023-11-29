package com.crocobet.usermanagement.service.facade;

import com.crocobet.usermanagement.dto.AuthResponseDTO;
import com.crocobet.usermanagement.dto.LoginRequestDTO;
import com.crocobet.usermanagement.exception.ForbiddenException;
import com.crocobet.usermanagement.persistence.entity.UserEntity;
import com.crocobet.usermanagement.service.TokenService;
import com.crocobet.usermanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceFacade {
    private final TokenService tokenService;
    private final UserService userService;

    public AuthResponseDTO login(LoginRequestDTO request) {
        UserEntity user = userService.findByEmailAndPassword(request.getEmail(), request.getPassword());

        if (!user.getActive()) {
            log.warn("Deactivated user with id: {} tried to sign in", user.getId());
            throw new ForbiddenException("User is deactivated");
        }

        String token = tokenService.generateAccessToken(
                user.getId().toString(),
                user.getRoles().stream().map(roleEntity -> roleEntity.getName().name()).toList());

        return new AuthResponseDTO(token);
    }

}
