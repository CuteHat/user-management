package com.crocobet.usermanagement.controller;

import com.crocobet.usermanagement.dto.AuthResponseDTO;
import com.crocobet.usermanagement.dto.LoginRequestDTO;
import com.crocobet.usermanagement.service.facade.AuthServiceFacade;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthServiceFacade authServiceFacade;

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Authenticate user and return access token, deactivated user cannot login")
    public AuthResponseDTO login(@RequestBody @Valid LoginRequestDTO request) {
        return authServiceFacade.login(request);
    }
}
