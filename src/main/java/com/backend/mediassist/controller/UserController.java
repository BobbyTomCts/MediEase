package com.backend.mediassist.controller;
import com.backend.mediassist.model.User;
import com.backend.mediassist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        if (userService.userExists(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Optional<User> registeredUser = userService.registerUser(user);
        return registeredUser
            .map(u -> ResponseEntity.status(HttpStatus.CREATED).body(u))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.findUserByEmail(email);
        return user
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        if (!userService.userExists(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Optional<User> updatedUser = userService.updateUser(user);
        return updatedUser
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }
}