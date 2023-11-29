package com.crocobet.usermanagement.controller.admin;

import com.crocobet.usermanagement.dto.AdminUserCreateDTO;
import com.crocobet.usermanagement.dto.AdminUserDTO;
import com.crocobet.usermanagement.dto.AdminUserUpdateDTO;
import com.crocobet.usermanagement.persistence.model.RoleName;
import com.crocobet.usermanagement.service.facade.AdminUserServiceFacade;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/admin/users")
@RequiredArgsConstructor
@Validated
public class AdminUserController {
    private final AdminUserServiceFacade userServiceFacade;

    @PostMapping
    @Operation(summary = "create user")
    public AdminUserDTO createUser(@RequestBody @Valid AdminUserCreateDTO request) {
        return userServiceFacade.createUser(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "update user")
    public AdminUserDTO updateUser(@PathVariable("id") @Min(1) Long id,
                                   @RequestBody @Valid AdminUserUpdateDTO request) {
        return userServiceFacade.updateUser(id, request);
    }

    @PatchMapping("/{id}/deactivate")
    @Operation(summary = "deactivate user, deactivated users cannot login")
    public AdminUserDTO deactivateUser(@PathVariable("id") @Min(1) Long id) {
        return userServiceFacade.deactivateUser(id);
    }

    @PatchMapping("/{id}/activate")
    @Operation(summary = "activate user, activated users can login")
    public AdminUserDTO activateUser(@PathVariable("id") @Min(1) Long id) {
        return userServiceFacade.activateUSer(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "get user by id")
    public AdminUserDTO getUser(@PathVariable("id") @Min(1) Long id) {
        return userServiceFacade.getUser(id);
    }

    @GetMapping("/filter")
    @Operation(summary = "filter users", description = "can be called without filters and will return all users")
    public Page<AdminUserDTO> filterUsers(@RequestParam(required = false) String email,
                                          @RequestParam(required = false) String name,
                                          @RequestParam(required = false) Boolean active,
                                          @RequestParam(required = false) List<RoleName> roles,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size) {
        return userServiceFacade.filterUsers(email, name, active, roles, page, size);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "delete user")
    public void deleteUser(@PathVariable("id") @Min(1) Long id) {
        userServiceFacade.deleteUser(id);
    }
}
