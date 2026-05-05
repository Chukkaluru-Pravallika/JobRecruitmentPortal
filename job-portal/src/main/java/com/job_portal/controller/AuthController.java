package com.job_portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.job_portal.model.User;
import com.job_portal.service.UserService;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    // Home page
    @GetMapping("/")
    public String home() {
        return "index";
    }

    // Show login page
    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    // Show register page
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    // Handle register form submit
    @PostMapping("/register")
    public String registerUser(
            @ModelAttribute("user") User user,
            @RequestParam("role") String role,
            Model model) {
        try {
            user.setRole(User.Role.valueOf(role));
            userService.registerUser(user);
            return "redirect:/login?registered=true";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }
    }
}