package com.job_portal.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.job_portal.model.User;
import com.job_portal.repository.UserRepository;

@Component
public class SecurityUtil {

    @Autowired
    private UserRepository userRepository;

    // Get currently logged in user
    public User getLoggedInUser() {
        Authentication auth = SecurityContextHolder
            .getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return null;
        }
        String email = auth.getName();
        return userRepository.findByEmail(email)
            .orElse(null);
    }

    // Get logged in user's email
    public String getLoggedInEmail() {
        Authentication auth = SecurityContextHolder
            .getContext().getAuthentication();
        return auth != null ? auth.getName() : null;
    }

    // Check if user is logged in
    public boolean isLoggedIn() {
        Authentication auth = SecurityContextHolder
            .getContext().getAuthentication();
        return auth != null && auth.isAuthenticated()
            && !auth.getName().equals("anonymousUser");
    }

    // Check if current user has a role
    public boolean hasRole(String role) {
        Authentication auth = SecurityContextHolder
            .getContext().getAuthentication();
        return auth != null && auth.getAuthorities()
            .stream()
            .anyMatch(a -> a.getAuthority()
                .equals("ROLE_" + role));
    }
}