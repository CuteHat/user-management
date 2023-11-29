package com.crocobet.usermanagement.service.facade;

import com.crocobet.usermanagement.dto.AdminUserCreateDTO;
import com.crocobet.usermanagement.dto.AdminUserDTO;
import com.crocobet.usermanagement.dto.AdminUserUpdateDTO;
import com.crocobet.usermanagement.exception.model.ExceptionFactory;
import com.crocobet.usermanagement.persistence.entity.RoleEntity;
import com.crocobet.usermanagement.persistence.entity.UserEntity;
import com.crocobet.usermanagement.persistence.model.RoleName;
import com.crocobet.usermanagement.service.RoleService;
import com.crocobet.usermanagement.service.UserService;
import com.crocobet.usermanagement.util.ListUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdminUserServiceFacade {
    private final UserService userService;
    private final RoleService roleService;

    public AdminUserDTO createUser(AdminUserCreateDTO request) {
        RoleEntity roleEntity = getRoleEntity(request.getRole());
        UserEntity user = userService.create(request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                request.getActive(),
                ListUtils.listOfOneOrNull(roleEntity));

        return AdminUserDTO.from(user);
    }

    public AdminUserDTO updateUser(Long id, AdminUserUpdateDTO request) {
        RoleEntity roleEntity = getRoleEntity(request.getRole());
        UserEntity user = userService.update(id,
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                ListUtils.listOfOneOrNull(roleEntity));

        return AdminUserDTO.from(user);
    }

    public AdminUserDTO deactivateUser(Long id) {
        UserEntity user = userService.deactivate(id);
        return AdminUserDTO.from(user);
    }

    public AdminUserDTO activateUSer(Long id) {
        UserEntity user = userService.activate(id);
        return AdminUserDTO.from(user);
    }

    public AdminUserDTO getUser(Long id) {
        UserEntity user = userService.findById(id);
        return AdminUserDTO.from(user);
    }

    public Page<AdminUserDTO> filterUsers(String email,
                                          String username,
                                          Boolean active,
                                          List<RoleName> role,
                                          int page,
                                          int size) {
        Page<UserEntity> entityPage = userService.filter(email, username, active, role, page, size);

        List<AdminUserDTO> adminUserDTOS = entityPage.stream()
                .map(AdminUserDTO::from)
                .toList();
        return new PageImpl<>(adminUserDTOS, entityPage.getPageable(), entityPage.getTotalElements());
    }

    private RoleEntity getRoleEntity(RoleName roleName) {
        RoleEntity roleEntity = null;
        if (roleName != null) {
            Optional<RoleEntity> optionalRole = roleService.getOptionalRoleByName(roleName);
            if (optionalRole.isEmpty()) {
                throw ExceptionFactory.createInvalidRequestException("role", "role not found");
            }
            roleEntity = optionalRole.get();
        }
        return roleEntity;
    }

    public void deleteUser(Long id) {
        userService.delete(id);
    }

}
