package com.crocobet.usermanagement.service.facade;

import com.crocobet.usermanagement.dto.UserDTO;
import com.crocobet.usermanagement.dto.UserUpdateDTO;
import com.crocobet.usermanagement.persistence.entity.UserEntity;
import com.crocobet.usermanagement.service.UserService;
import com.crocobet.usermanagement.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceFacade {
    private final UserService userService;

    public UserDTO me() {
        Long userId = SecurityUtils.getAuthenticatedUserId();
        UserEntity userEntity = userService.findById(userId);
        return UserDTO.from(userEntity);
    }

    public UserDTO update(UserUpdateDTO userUpdateDTO) {
        Long userId = SecurityUtils.getAuthenticatedUserId();
        UserEntity userEntity = userService.findById(userId);

        userEntity = userService.update(userId,
                userUpdateDTO.getUsername(),
                userUpdateDTO.getEmail(),
                userUpdateDTO.getPassword(),
                userEntity.getRoles());
        return UserDTO.from(userEntity);
    }
}