package com.crocobet.usermanagement.service.facade;

import com.crocobet.usermanagement.dto.UserDTO;
import com.crocobet.usermanagement.dto.UserUpdateDTO;
import com.crocobet.usermanagement.persistence.entity.UserEntity;
import com.crocobet.usermanagement.service.UserService;
import com.crocobet.usermanagement.util.SecurityUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceFacadeTest {
    @InjectMocks
    private UserServiceFacade userServiceFacade;

    @Mock
    private UserService userService;

    private final Long testUserId = 1L;
    private MockedStatic<SecurityUtils> mockedSecurityUtils;

    @BeforeEach
    void setup() {
        mockedSecurityUtils = Mockito.mockStatic(SecurityUtils.class);
        mockedSecurityUtils.when(SecurityUtils::getAuthenticatedUserId).thenReturn(testUserId);
    }

    @AfterEach
    void tearDown() {
        mockedSecurityUtils.close();
    }

    @Test
    void whenMe_thenReturnUser() {
        UserEntity userEntity = mock(UserEntity.class);
        when(userService.findById(testUserId)).thenReturn(userEntity);
        UserDTO result = userServiceFacade.me();
        assertNotNull(result);
    }

    @Test
    void whenUpdate_thenReturnUpdatedUser() {
        UserEntity userEntity = mock(UserEntity.class);
        when(userService.findById(testUserId)).thenReturn(userEntity);
        when(userService.update(any(), any(), any(), any(), any())).thenReturn(userEntity);
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        UserDTO result = userServiceFacade.update(userUpdateDTO);
        assertNotNull(result);
    }
}