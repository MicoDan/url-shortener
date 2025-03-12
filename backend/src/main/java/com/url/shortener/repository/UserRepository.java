package com.url.shortener.repository;

import com.url.shortener.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for user-related database operations
 * Handles user entity persistence and retrieval
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Find a user by their username
    Optional<User> findByUsername(String username);
}
