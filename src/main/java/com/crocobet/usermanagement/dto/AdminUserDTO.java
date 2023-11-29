package com.crocobet.usermanagement.dto;

import com.crocobet.usermanagement.persistence.entity.RoleEntity;
import com.crocobet.usermanagement.persistence.entity.UserEntity;
import com.crocobet.usermanagement.persistence.model.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class AdminUserDTO {
    private Long id;
    private String email;
    private String username;
    private List<RoleName> roles;
    private Boolean active;
    private OffsetDateTime createdAt;
    private OffsetDateTime updateAt;

    public static AdminUserDTO from(UserEntity userEntity) {
        return new AdminUserDTO(userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getUsername(),
                userEntity.getRoles()
                        .stream()
                        .map(RoleEntity::getName)
                        .toList(),
                userEntity.getActive(),
                userEntity.getCreatedAt(),
                userEntity.getUpdatedAt());
    }
}
