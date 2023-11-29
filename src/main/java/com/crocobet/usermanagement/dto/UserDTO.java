package com.crocobet.usermanagement.dto;

import com.crocobet.usermanagement.persistence.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private String username;
    private OffsetDateTime createdAt;
    private OffsetDateTime updateAt;

    public static UserDTO from(UserEntity userEntity) {
        return new UserDTO(userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getUsername(),
                userEntity.getCreatedAt(),
                userEntity.getUpdatedAt());
    }
}
