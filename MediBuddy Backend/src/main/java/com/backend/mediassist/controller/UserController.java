package com.backend.mediassist.controller;

import com.backend.mediassist.model.LoginResponse;
import com.backend.mediassist.model.User;
import com.backend.mediassist.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "User Management", description = "APIs for user registration, login, and profile management")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Operation(summary = "Register new user", description = "Create a new user account in the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User registered successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input or user already exists")
    })
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }
    
    @Operation(summary = "User login", description = "Authenticate user and return JWT token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login successful"),
        @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public LoginResponse login(
            @Parameter(description = "User email") @RequestParam String email,
            @Parameter(description = "User password") @RequestParam String password) {
        return userService.loginUser(email, password);
    }
    
    @Operation(summary = "Validate JWT token", description = "Validate token and return user details")
    @GetMapping("/validate")
    public User validateToken(@RequestHeader("Authorization") String token) {
        // Remove "Bearer " prefix if present
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return userService.validateTokenAndGetUser(token);
    }
    
    @Operation(summary = "Get user by ID", description = "Retrieve user details by user ID")
    @GetMapping("/{id}")
    public User getUser(@Parameter(description = "User ID") @PathVariable Long id) {
        return userService.getUserById(id);
    }
    
    @Operation(summary = "Update user profile", description = "Update user name and phone number")
    @PutMapping("/{id}")
    public User updateUser(
            @Parameter(description = "User ID") @PathVariable Long id,
            @RequestBody User user) {
        return userService.updateUser(id, user);
    }
    
    @Operation(summary = "Check admin status", description = "Check if user has admin privileges")
    @GetMapping("/isAdmin/{userId}")
    public boolean isAdmin(@Parameter(description = "User ID") @PathVariable Long userId) {
        return userService.isAdmin(userId);
    }
}