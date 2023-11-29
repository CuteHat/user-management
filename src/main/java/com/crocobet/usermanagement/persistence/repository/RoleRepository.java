package com.crocobet.usermanagement.persistence.repository;

import com.crocobet.usermanagement.persistence.entity.RoleEntity;
import com.crocobet.usermanagement.persistence.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(RoleName name);

}
