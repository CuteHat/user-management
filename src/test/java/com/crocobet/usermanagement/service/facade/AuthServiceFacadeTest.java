package com.crocobet.usermanagement.service.facade;

import com.crocobet.usermanagement.dto.AuthResponseDTO;
import com.crocobet.usermanagement.dto.LoginRequestDTO;
import com.crocobet.usermanagement.exception.ForbiddenException;
import com.crocobet.usermanagement.persistence.entity.RoleEntity;
import com.crocobet.usermanagement.persistence.entity.UserEntity;
import com.crocobet.usermanagement.persistence.model.RoleName;
import com.crocobet.usermanagement.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class AuthServiceFacadeTest {
    @Autowired
    private AuthServiceFacade authServiceFacade;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    private UserEntity activeUser;
    private UserEntity deactivatedUser;


    @BeforeEach
    void setup() {
        userRepository.deleteAll();

        activeUser = new UserEntity();
        activeUser.setUsername("activeUser");
        activeUser.setEmail("activeuser@example.com");
        activeUser.setPassword(passwordEncoder.encode("password"));
        activeUser.setActive(true);
        userRepository.save(activeUser);

        deactivatedUser = new UserEntity();
        deactivatedUser.setUsername("deactivatedUser");
        deactivatedUser.setEmail("deactivateduser@example.com");
        deactivatedUser.setPassword(passwordEncoder.encode("password"));
        deactivatedUser.setActive(false);
        userRepository.save(deactivatedUser);
    }

    @Test
    void whenLoginWithValidCredentials_thenSucceed() {
        LoginRequestDTO request = new LoginRequestDTO("activeuser@example.com", "password");
        AuthResponseDTO response = authServiceFacade.login(request);
        assertNotNull(response.getAccessToken());
    }

    @Test
    void whenLoginDeactivatedUser_thenThrowForbiddenException() {
        LoginRequestDTO request = new LoginRequestDTO("deactivateduser@example.com", "password");
        assertThrows(ForbiddenException.class, () -> authServiceFacade.login(request));
    }
}