package com.job_portal.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {

        // Redirect based on role
        if (authentication.getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_SEEKER"))) {
            response.sendRedirect("/seeker/dashboard");

        } else if (authentication.getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_RECRUITER"))) {
            response.sendRedirect("/recruiter/dashboard");

        } else if (authentication.getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            response.sendRedirect("/admin/dashboard");

        } else {
            response.sendRedirect("/");
        }
    }
}