package com.crocobet.usermanagement.service;

import com.crocobet.usermanagement.exception.InvalidRequestException;
import com.crocobet.usermanagement.exception.UnauthorizedException;
import com.crocobet.usermanagement.persistence.entity.RoleEntity;
import com.crocobet.usermanagement.persistence.entity.UserEntity;
import com.crocobet.usermanagement.persistence.model.RoleName;
import com.crocobet.usermanagement.persistence.repository.RoleRepository;
import com.crocobet.usermanagement.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private RoleEntity testRole;
    private Long testUserId;
    private final String testUsername = "testUser";
    private final String testEmail = "test123@example.com";
    private final String testPassword = "pA$2ssword";

    @Container
    static PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    static {
        postgresqlContainer.start();
        System.setProperty("spring.datasource.url", postgresqlContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgresqlContainer.getUsername());
        System.setProperty("spring.datasource.password", postgresqlContainer.getPassword());
    }

    @BeforeEach
    public void setup() {
        testRole = roleRepository.findByName(RoleName.ADMINISTRATOR).get();
        userRepository.deleteAll();
        UserEntity createdUser = userService.create(testUsername, testEmail, testPassword, true, Collections.singletonList(testRole));
        testUserId = createdUser.getId();
    }

    @Test
    @Order(1)
    public void testCreateUser() {
        UserEntity createdUser = userService.create(testUsername, "testme@gmail.com", testPassword, true, Collections.singletonList(testRole));
        assertNotNull(createdUser);
        assertTrue(passwordEncoder.matches(testPassword, createdUser.getPassword()));
    }

    @Test
    @Order(2)
    public void testUpdateUser() {
        System.out.println("testUserId: " + testUserId);
        UserEntity updatedUser = userService.update(testUserId, "newUsername", "newemail@example.com", "ne3WPassword", Collections.singletonList(testRole));
        assertNotNull(updatedUser);
        assertEquals("newemail@example.com", updatedUser.getEmail());
    }

    @Test
    public void testActivateUser() {
        UserEntity activatedUser = userService.activate(testUserId);
        assertTrue(activatedUser.getActive());
    }

    @Test
    public void testDeactivateUser() {
        UserEntity deactivatedUser = userService.deactivate(testUserId);
        assertFalse(deactivatedUser.getActive());
    }

    @Test
    public void testFindById() {
        UserEntity foundUser = userService.findById(testUserId);
        assertNotNull(foundUser);
    }

    @Test
    public void testFindByEmail() {
        UserEntity foundUser = userService.findByEmail(testEmail);
        assertNotNull(foundUser);
    }

    @Test
    public void testFindByEmailAndPassword() {
        UserEntity foundUser = userService.findByEmailAndPassword(testEmail, testPassword);
        assertNotNull(foundUser);
    }

    @Test
    public void testDeleteUser() {
        userService.delete(testUserId);
        assertFalse(userRepository.findById(testUserId).isPresent());
    }

    @Test
    public void testFilterUsers() {
        Page<UserEntity> users = userService.filter(null, null, true, Collections.singletonList(RoleName.ADMINISTRATOR), 0, 10);
        assertFalse(users.isEmpty());
    }

    @Test
    void whenCreatingUserWithDuplicateEmail_thenThrowException() {
        assertThrows(InvalidRequestException.class, () -> {
            userService.create("newUser", testEmail, "newPassword", true, Collections.emptyList());
        });
    }

    @Test
    void whenFindByEmailAndPasswordWithWrongPassword_thenThrowUnauthorizedException() {
        assertThrows(UnauthorizedException.class, () -> {
            userService.findByEmailAndPassword(testEmail, "incorrectPassword");
        });
    }
}