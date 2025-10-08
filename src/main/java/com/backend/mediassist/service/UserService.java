package com.backend.mediassist.service;
import com.backend.mediassist.model.User;
import java.util.Optional;

public interface UserService {
    Optional<User> registerUser(User user);
    Optional<User> findUserByEmail(String email);
    boolean userExists(String email);
    Optional<User> updateUser(User user);
    
}