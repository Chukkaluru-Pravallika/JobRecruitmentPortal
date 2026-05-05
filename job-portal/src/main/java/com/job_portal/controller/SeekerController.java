package com.job_portal.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.job_portal.model.Job;
import com.job_portal.model.User;
import com.job_portal.service.ApplicationService;
import com.job_portal.service.JobService;
import com.job_portal.service.UserService;

@Controller
@RequestMapping("/seeker")
public class SeekerController {

    @Autowired
    private UserService userService;

    @Autowired
    private JobService jobService;

    @Autowired
    private ApplicationService applicationService;
    
    @Autowired
    private com.job_portal.repository.UserRepository
        userRepository;

    // Upload directory — outside target folder
    private final String UPLOAD_DIR =
        System.getProperty("user.dir")
        + "/src/main/resources/static/uploads/";

    // Helper to get logged in user
    private User getLoggedInUser(Authentication auth) {
        return userService.findByEmail(
            auth.getName()).orElseThrow();
    }

    // Seeker dashboard
    @GetMapping("/dashboard")
    public String dashboard(
            Authentication auth, Model model) {
        User seeker = getLoggedInUser(auth);
        model.addAttribute("seeker", seeker);
        model.addAttribute("applications",
            applicationService
                .getApplicationsBySeeker(seeker));
        return "seeker/dashboard";
    }

    // Browse all open jobs
    @GetMapping("/jobs")
    public String browseJobs(
            @RequestParam(required = false)
                String keyword,
            @RequestParam(required = false)
                String category,
            @RequestParam(required = false)
                String location,
            Authentication auth, Model model) {

        User seeker = getLoggedInUser(auth);
        List<Job> jobs;

        if (keyword != null
                && !keyword.trim().isEmpty()) {
            jobs = jobService.searchJobs(
                keyword.trim());
        } else if (category != null
                && !category.trim().isEmpty()) {
            jobs = jobService.filterByCategory(
                category.trim());
        } else if (location != null
                && !location.trim().isEmpty()) {
            jobs = jobService.filterByLocation(
                location.trim());
        } else {
            jobs = jobService.getAllOpenJobs();
        }

        model.addAttribute("jobs", jobs);
        model.addAttribute("seeker", seeker);
        model.addAttribute("keyword", keyword);
        model.addAttribute("category", category);
        model.addAttribute("location", location);
        return "seeker/jobList";
    }

    // View job details
    @GetMapping("/jobs/{id}")
    public String jobDetails(
            @PathVariable Long id,
            Authentication auth, Model model) {

        User seeker = getLoggedInUser(auth);
        Job job = jobService.getJobById(id)
            .orElseThrow();
        boolean hasApplied =
            applicationService.hasApplied(
                seeker, job);

        model.addAttribute("job", job);
        model.addAttribute("hasApplied", hasApplied);
        return "seeker/jobDetails";
    }

    // Apply for a job
    @PostMapping("/jobs/{id}/apply")
    public String applyForJob(
            @PathVariable Long id,
            @RequestParam String coverLetter,
            Authentication auth, Model model) {

        User seeker = getLoggedInUser(auth);
        Job job = jobService.getJobById(id)
            .orElseThrow();

        try {
            applicationService.applyForJob(
                seeker, job, coverLetter);
            return "redirect:/seeker/dashboard"
                + "?applied=true";
        } catch (RuntimeException e) {
            model.addAttribute("error",
                e.getMessage());
            model.addAttribute("job", job);
            model.addAttribute("hasApplied", true);
            return "seeker/jobDetails";
        }
    }

    // View seeker profile
 // View seeker profile
    @GetMapping("/profile")
    public String profile(
            Authentication auth, Model model) {
        // Always get fresh user from DB
        String email = auth.getName();
        User seeker = userRepository
            .findByEmail(email)
            .orElseThrow();
        model.addAttribute("seeker", seeker);
        return "seeker/profile";
    }
    
    // Upload resume
    @PostMapping("/profile/resume")
    public String uploadResume(
            @RequestParam("resume") MultipartFile file,
            Authentication auth) throws IOException {

        // Validate PDF only
        String originalFileName =
            file.getOriginalFilename();
        if (originalFileName == null
                || !originalFileName
                    .toLowerCase().endsWith(".pdf")) {
            return "redirect:/seeker/profile"
                + "?error=onlypdf";
        }

        // Validate size — max 5MB
        if (file.getSize() > 5 * 1024 * 1024) {
            return "redirect:/seeker/profile"
                + "?error=toolarge";
        }

        // Get logged in user email
        String email = auth.getName();

        // Get FRESH user from database
        User seeker = userRepository
            .findByEmail(email)
            .orElseThrow();

        // Create uploads directory if not exists
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Save file with unique name
        String savedFileName = seeker.getId()
            + "_" + originalFileName;
        Path filePath = uploadPath
            .resolve(savedFileName);
        Files.copy(file.getInputStream(),
            filePath,
            StandardCopyOption.REPLACE_EXISTING);

        // Update resume path directly in DB
        String resumePath = "/uploads/" + savedFileName;
        seeker.setResumePath(resumePath);
        userRepository.save(seeker);

        // Verify it saved
        System.out.println("Resume path saved: "
            + resumePath);

        return "redirect:/seeker/profile"
            + "?uploaded=true";
    }
    }