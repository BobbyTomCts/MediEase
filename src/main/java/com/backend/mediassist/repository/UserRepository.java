package com.backend.mediassist.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.backend.mediassist.model.User;
import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}