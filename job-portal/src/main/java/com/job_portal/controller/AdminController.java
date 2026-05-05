package com.job_portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.job_portal.service.JobService;
import com.job_portal.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private JobService jobService;

    // Admin dashboard
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalSeekers",
            userService.getAllSeekers().size());
        model.addAttribute("totalRecruiters",
            userService.getAllRecruiters().size());
        model.addAttribute("totalJobs",
            jobService.getAllOpenJobs().size());
        model.addAttribute("seekers",
            userService.getAllSeekers());
        model.addAttribute("recruiters",
            userService.getAllRecruiters());
        return "admin/dashboard";
    }

    // Delete user
    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/dashboard?deleted=true";
    }

    // View all jobs
    @GetMapping("/jobs")
    public String allJobs(Model model) {
        model.addAttribute("jobs", jobService.getAllOpenJobs());
        return "admin/jobs";
    }

    // Delete job
    @PostMapping("/jobs/{id}/delete")
    public String deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return "redirect:/admin/jobs?deleted=true";
    }
}