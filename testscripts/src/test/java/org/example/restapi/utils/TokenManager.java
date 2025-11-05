package org.example.restapi.utils;

import io.restassured.response.Response;
import org.example.restapi.models.User;
import org.example.restapi.services.UserService;

/**
 * TokenManager: Singleton class to manage authentication tokens across test scenarios
 * This ensures tests can obtain and share tokens without re-logging in unnecessarily
 */
public class TokenManager {
    private static TokenManager instance;
    private String userToken;
    private Long userId;
    private String adminToken;
    private Long adminId;
    private final UserService userService;

    private TokenManager() {
        this.userService = new UserService();
    }

    public static TokenManager getInstance() {
        if (instance == null) {
            instance = new TokenManager();
        }
        return instance;
    }

    /**
     * Login as a regular user and store token
     */
    public String loginAsUser(String email, String password) {
        User user = new User(email, password);
        Response response = userService.postRequest1("/api/users/login", user);
        
        if (response.getStatusCode() == 200) {
            userToken = response.jsonPath().getString("token");
            userId = response.jsonPath().getLong("id");
            System.out.println("User logged in successfully. ID: " + userId);
            return userToken;
        } else {
            throw new RuntimeException("User login failed with status: " + response.getStatusCode());
        }
    }

    /**
     * Login as admin and store token
     */
    public String loginAsAdmin(String email, String password) {
        User admin = new User(email, password);
        Response response = userService.postRequest1("/api/users/login", admin);
        
        if (response.getStatusCode() == 200) {
            adminToken = response.jsonPath().getString("token");
            adminId = response.jsonPath().getLong("id");
            System.out.println("Admin logged in successfully. ID: " + adminId);
            return adminToken;
        } else {
            throw new RuntimeException("Admin login failed with status: " + response.getStatusCode());
        }
    }

    /**
     * Get current user token (login if not already logged in)
     */
    public String getUserToken() {
        if (userToken == null || userToken.isEmpty()) {
            // Default test user credentials - modify as needed
            return loginAsUser("john.doe@example.com", "password123");
        }
        return userToken;
    }

    /**
     * Get current admin token (login if not already logged in)
     */
    public String getAdminToken() {
        if (adminToken == null || adminToken.isEmpty()) {
            // Default admin credentials - modify as needed
            return loginAsAdmin("admin@medibuddy.com", "admin123");
        }
        return adminToken;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getAdminId() {
        return adminId;
    }

    /**
     * Clear all stored tokens (useful for test cleanup)
     */
    public void clearTokens() {
        userToken = null;
        userId = null;
        adminToken = null;
        adminId = null;
    }
}
