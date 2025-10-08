package com.backend.mediassist.service;

import com.backend.mediassist.model.User;
import com.backend.mediassist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service  // Makes this class a Spring-managed service bean
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> registerUser(User user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return Optional.empty(); // Or throw a custom exception
        }
        return Optional.of(userRepository.save(user));
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> updateUser(User user) {
        // You might want to check if the user exists before updating
        if (!userRepository.existsById(user.getId())) {
            return Optional.empty(); // Or throw a custom exception
        }
        return Optional.of(userRepository.save(user));
    }

    @Override
    public boolean userExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
