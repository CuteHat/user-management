package com.crocobet.usermanagement.service;

import com.crocobet.usermanagement.exception.NotFoundException;
import com.crocobet.usermanagement.exception.UnauthorizedException;
import com.crocobet.usermanagement.exception.model.ExceptionFactory;
import com.crocobet.usermanagement.persistence.entity.RoleEntity;
import com.crocobet.usermanagement.persistence.entity.UserEntity;
import com.crocobet.usermanagement.persistence.model.RoleName;
import com.crocobet.usermanagement.persistence.repository.UserRepository;
import com.crocobet.usermanagement.persistence.specification.UserSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @CachePut(value = "users", key = "#result.id")
    public UserEntity create(String username, String email, String password, Boolean active, Collection<RoleEntity> roles) {
        validateEmailUniqueness(email);

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setEmail(email.toLowerCase());
        user.setPassword(passwordEncoder.encode(password));
        user.setActive(active);
        user.setRoles(new ArrayList<>(roles));

        return userRepository.save(user);
    }

    @CachePut(value = "users", key = "#userId")
    public UserEntity update(Long userId, String username, String email, String password, Collection<RoleEntity> roles) {
        return update(findById(userId), username, email, password, roles);
    }

    private UserEntity update(UserEntity user, String username, String email, String password, Collection<RoleEntity> roles) {
        if (!user.getEmail().equals(email))
            validateEmailUniqueness(email);

        user.setUsername(username);
        user.setEmail(email.toLowerCase());
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(new ArrayList<>(roles));
        return userRepository.save(user);
    }

    @CachePut(value = "users", key = "#result.id")
    public UserEntity deactivate(Long userId) {
        return setActive(userId, false);
    }

    @CachePut(value = "users", key = "#result.id")
    public UserEntity activate(Long userId) {
        return setActive(userId, true);
    }


    private UserEntity setActive(Long userId, Boolean active) {
        UserEntity user = findById(userId);
        user.setActive(active);
        return userRepository.save(user);
    }

    @Cacheable(value = "users", key = "#id")
    public UserEntity findById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("user", id.toString()));
    }

    private void validateEmailUniqueness(String email) {
        if (userRepository.exists(UserSpecifications.emailEquals(email))) {
            throw ExceptionFactory.createInvalidRequestException("email", "email must be unique");
        }
    }

    @Cacheable(value = "filteredUsers", key = "{#email, #username, #active, #roles, #page, #size}")
    public Page<UserEntity> filter(String email,
                                   String username,
                                   Boolean active,
                                   Collection<RoleName> roles,
                                   int page,
                                   int size) {
        return userRepository.findAll(UserSpecifications.optEmailEquals(email)
                .and(UserSpecifications.optUsernameEquals(username))
                .and(UserSpecifications.optIsActive(active))
                .and(UserSpecifications.optHasRoles(roles)), PageRequest.of(page, size));
    }

    @CacheEvict(value = {"users", "usersByEmail", "filteredUsers"}, allEntries = true)
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Cacheable(value = "usersByEmail", key = "#email")
    public UserEntity findByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new NotFoundException("user", email));
    }

    public UserEntity findByEmailAndPassword(String email, String password) {
        UserEntity user = findByEmail(email);
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new UnauthorizedException("Invalid credentials");
        return user;
    }
}
