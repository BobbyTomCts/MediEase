package com.backend.mediassist.service;

import com.backend.mediassist.config.JwtUtil;
import com.backend.mediassist.model.LoginResponse;
import com.backend.mediassist.model.User;
import com.backend.mediassist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    // Register new user
    public User registerUser(User user) {
        // Validate input
        if (user == null) {
            throw new RuntimeException("User data is required");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new RuntimeException("Email is required");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new RuntimeException("Password is required");
        }
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new RuntimeException("Name is required");
        }
        
        // Check if email already exists
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new RuntimeException("User account already exists with this email");
        }
        
        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Set default role if not provided
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("EMPLOYEE");
        }
        
        return userRepository.save(user);
    }
    
    // Login user with JWT token
    public LoginResponse loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        
        if (user == null) {
            return new LoginResponse(null, null, null, null, false, "User not found", null);
        }
        
        // Verify password
        if (passwordEncoder.matches(password, user.getPassword())) {
            boolean isAdmin = "ADMIN".equalsIgnoreCase(user.getRole());
            
            // Generate JWT token
            String token = jwtUtil.generateToken(user.getEmail(), user.getId(), user.getRole());
            
            return new LoginResponse(
                user.getId(), 
                user.getName(), 
                user.getEmail(), 
                user.getRole(), 
                isAdmin, 
                "Login successful",
                token // Include JWT token
            );
        } else {
            return new LoginResponse(null, null, null, null, false, "Invalid password", null);
        }
    }
    
    // Get user by ID
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    
    // Check if user is admin
    public boolean isAdmin(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return "ADMIN".equalsIgnoreCase(user.getRole());
        }
        return false;
    }
    
    // Validate token and get user
    public User validateTokenAndGetUser(String token) {
        if (jwtUtil.validateToken(token)) {
            Long userId = jwtUtil.getUserIdFromToken(token);
            return getUserById(userId);
        }
        return null;
    }
}