package com.crocobet.usermanagement.service;

import com.crocobet.usermanagement.persistence.entity.RoleEntity;
import com.crocobet.usermanagement.persistence.model.RoleName;
import com.crocobet.usermanagement.persistence.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository repository;

    @Cacheable(value = "roles", key = "#name")
    public Optional<RoleEntity> getOptionalRoleByName(RoleName name) {
        return repository.findByName(name);
    }

}
