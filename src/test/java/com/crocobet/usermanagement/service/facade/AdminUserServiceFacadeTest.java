package com.crocobet.usermanagement.service.facade;

import com.crocobet.usermanagement.dto.AdminUserCreateDTO;
import com.crocobet.usermanagement.dto.AdminUserDTO;
import com.crocobet.usermanagement.dto.AdminUserUpdateDTO;
import com.crocobet.usermanagement.persistence.entity.RoleEntity;
import com.crocobet.usermanagement.persistence.entity.UserEntity;
import com.crocobet.usermanagement.persistence.model.RoleName;
import com.crocobet.usermanagement.service.RoleService;
import com.crocobet.usermanagement.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminUserServiceFacadeTest {
    @InjectMocks
    private AdminUserServiceFacade adminUserServiceFacade;

    @Mock
    private UserService userService;

    @Mock
    private RoleService roleService;

    private final Long userId = 1L;
    private final RoleName roleName = RoleName.ADMINISTRATOR;
    private RoleEntity roleEntity;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        roleEntity = new RoleEntity();
        userEntity = new UserEntity();
        userEntity.setId(userId);
        userEntity.setUsername("testUser");
        userEntity.setEmail("test@example.com");
        userEntity.setActive(true);
        userEntity.setRoles(Collections.singletonList(roleEntity));
    }

    @Test
    void createUser() {
        when(roleService.getOptionalRoleByName(roleName)).thenReturn(Optional.of(roleEntity));
        when(userService.create(any(), any(), any(), any(), any())).thenReturn(userEntity);

        AdminUserCreateDTO request = new AdminUserCreateDTO("newUser", "new@example.com", "password", roleName, true);
        AdminUserDTO result = adminUserServiceFacade.createUser(request);

        assertNotNull(result);
    }

    @Test
    void updateUser() {
        when(roleService.getOptionalRoleByName(roleName)).thenReturn(Optional.of(roleEntity));
        when(userService.update(any(), any(), any(), any(), any())).thenReturn(userEntity);

        AdminUserUpdateDTO request = new AdminUserUpdateDTO("updatedUser", "updated@example.com", "newPassword", roleName);
        AdminUserDTO result = adminUserServiceFacade.updateUser(userId, request);

        assertNotNull(result);
    }

    @Test
    void deactivateUser() {
        when(userService.deactivate(any())).thenReturn(userEntity);

        AdminUserDTO result = adminUserServiceFacade.deactivateUser(userId);

        assertNotNull(result);
    }

    @Test
    void activateUser() {
        when(userService.activate(any())).thenReturn(userEntity);

        AdminUserDTO result = adminUserServiceFacade.activateUSer(userId);

        assertNotNull(result);
    }

    @Test
    void getUser() {
        when(userService.findById(any())).thenReturn(userEntity);

        AdminUserDTO result = adminUserServiceFacade.getUser(userId);

        assertNotNull(result);
    }

    @Test
    void filterUsers() {
        Page<UserEntity> userEntityPage = new PageImpl<>(Collections.singletonList(userEntity));
        when(userService.filter(any(), any(), any(), any(), anyInt(), anyInt())).thenReturn(userEntityPage);

        Page<AdminUserDTO> result = adminUserServiceFacade.filterUsers("test@example.com", "testUser", true, Collections.singletonList(roleName), 0, 10);

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void deleteUser() {
        adminUserServiceFacade.deleteUser(userId);
        verify(userService).delete(userId);
    }
}