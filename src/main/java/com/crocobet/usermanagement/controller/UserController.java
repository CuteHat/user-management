package com.crocobet.usermanagement.controller;

import com.crocobet.usermanagement.dto.UserDTO;
import com.crocobet.usermanagement.dto.UserUpdateDTO;
import com.crocobet.usermanagement.service.facade.UserServiceFacade;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceFacade userServiceFacade;

    @GetMapping
    @Operation(summary = "Get authenticated user")
    public UserDTO me() {
        return userServiceFacade.me();
    }

    @PutMapping
    @Operation(summary = "Update authenticated user")
    public UserDTO update(@RequestBody @Valid UserUpdateDTO request) {
        return userServiceFacade.update(request);
    }
}
