package com.job_portal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.job_portal.model.User;
import com.job_portal.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Register new user
    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered!");
        }
        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Find user by email
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Find user by id
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    // Get all seekers
    public List<User> getAllSeekers() {
        return userRepository.findByRole(User.Role.SEEKER);
    }

    // Get all recruiters
    public List<User> getAllRecruiters() {
        return userRepository.findByRole(User.Role.RECRUITER);
    }

    // Update user profile
    public User updateUser(User user) {
        return userRepository.save(user);
    }
    

    // Delete user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}