package com.example.testasync.repository;

import com.example.testasync.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@SuppressWarnings("NullableProblems")
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
}
