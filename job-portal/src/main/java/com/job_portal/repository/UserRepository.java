package com.job_portal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.job_portal.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by email (used in login)
    Optional<User> findByEmail(String email);

    // Check if email already exists (used in register)
    boolean existsByEmail(String email);

    // Find all users by role
    java.util.List<User> findByRole(User.Role role);
}