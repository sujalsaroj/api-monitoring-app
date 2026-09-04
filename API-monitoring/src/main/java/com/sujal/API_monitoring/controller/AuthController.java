package com.sujal.API_monitoring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sujal.API_monitoring.dto.auth.AuthResponse;
import com.sujal.API_monitoring.dto.auth.LoginRequest;
import com.sujal.API_monitoring.dto.auth.RegisterRequest;
import com.sujal.API_monitoring.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(
    name = "Authentication",
    description = "APIs for user registration and login"
)
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(
        summary = "Register user",
        description = "Creates a new user account"
    )
    @ApiResponse(
        responseCode = "201",
        description = "User registered successfully"
    )
    @ApiResponse(
        responseCode = "400",
        description = "Invalid user input"
    )
    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody RegisterRequest request) {

        authService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User registered successfully");
    }

    @Operation(
        summary = "Login user",
        description = "Authenticates the user and returns a JWT token"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Login successful"
    )
    @ApiResponse(
        responseCode = "400",
        description = "Invalid request data"
    )
    @ApiResponse(
        responseCode = "401",
        description = "Invalid email or password"
    )
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                authService.login(request)
        );
    }
}