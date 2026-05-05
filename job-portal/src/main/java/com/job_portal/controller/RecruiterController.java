package com.job_portal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.job_portal.model.Application;
import com.job_portal.model.Company;
import com.job_portal.model.Job;
import com.job_portal.model.User;
import com.job_portal.service.ApplicationService;
import com.job_portal.service.CompanyService;
import com.job_portal.service.JobService;
import com.job_portal.service.UserService;

@Controller
@RequestMapping("/recruiter")
public class RecruiterController {

    @Autowired
    private UserService userService;

    @Autowired
    private JobService jobService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private CompanyService companyService;

    // Helper to get logged in user
    private User getLoggedInUser(Authentication auth) {
        return userService.findByEmail(
            auth.getName()).orElseThrow();
    }

    // Recruiter dashboard
    @GetMapping("/dashboard")
    public String dashboard(
            Authentication auth, Model model) {
        User recruiter = getLoggedInUser(auth);
        List<Job> jobs =
            jobService.getJobsByRecruiter(recruiter);
        model.addAttribute("recruiter", recruiter);
        model.addAttribute("jobs", jobs);
        model.addAttribute("totalJobs", jobs.size());
        return "recruiter/dashboard";
    }

 // Show post job form
    @GetMapping("/jobs/post")
    public String postJobForm(
            Authentication auth, Model model) {
        User recruiter = getLoggedInUser(auth);
        List<Company> companies = companyService
            .getCompaniesByRecruiter(recruiter);

        if (companies.isEmpty()) {
            return "redirect:/recruiter/company/new"
                + "?error=nocompany";
        }

        model.addAttribute("job", new Job());
        model.addAttribute("companies", companies);
        return "recruiter/postJob";
    }

    // Handle post job submit
    @PostMapping("/jobs/post")
    public String postJob(
            @ModelAttribute Job job,
            @RequestParam(required = false) Long companyId,
            Authentication auth,
            Model model) {

        User recruiter = getLoggedInUser(auth);
        List<Company> companies = companyService
            .getCompaniesByRecruiter(recruiter);

        if (companyId == null) {
            model.addAttribute("job", job);
            model.addAttribute("companies", companies);
            model.addAttribute("error",
                "Please select a company!");
            return "recruiter/postJob";
        }

        Optional<Company> companyOpt = companyService
            .getCompanyById(companyId);

        if (companyOpt.isEmpty()) {
            model.addAttribute("job", job);
            model.addAttribute("companies", companies);
            model.addAttribute("error",
                "Selected company not found!");
            return "recruiter/postJob";
        }

        job.setRecruiter(recruiter);
        job.setCompany(companyOpt.get());
        jobService.postJob(job);
        return "redirect:/recruiter/dashboard"
            + "?posted=true";
    }

    // View applicants for a job
    @GetMapping("/jobs/{id}/applicants")
    public String viewApplicants(
            @PathVariable Long id,
            Model model) {
        Job job = jobService.getJobById(id)
            .orElseThrow();
        model.addAttribute("job", job);
        model.addAttribute("applications",
            applicationService
                .getApplicationsByJob(job));
        return "recruiter/applicants";
    }

    // Update application status
    @PostMapping("/applications/{id}/status")
    public String updateStatus(
            @PathVariable Long id,
            @RequestParam String status,
            @RequestParam Long jobId) {
        applicationService.updateStatus(
            id,
            Application.ApplicationStatus
                .valueOf(status));
        return "redirect:/recruiter/jobs/"
            + jobId + "/applicants";
    }

    // Close a job
    @PostMapping("/jobs/{id}/close")
    public String closeJob(@PathVariable Long id) {
        jobService.closeJob(id);
        return "redirect:/recruiter/dashboard"
            + "?closed=true";
    }

    // Show existing company profile
    @GetMapping("/company")
    public String companyProfile(
            Authentication auth, Model model) {
        User recruiter = getLoggedInUser(auth);
        Company company = companyService
            .getCompanyByRecruiter(recruiter)
            .orElse(new Company());
        model.addAttribute("company", company);
        return "recruiter/company";
    }

    // Show blank form for new company
    @GetMapping("/company/new")
    public String newCompanyForm(Model model) {
        model.addAttribute("company", new Company());
        return "recruiter/company";
    }

    // Save company profile
    @PostMapping("/company")
    public String saveCompany(
            @RequestParam(required = false) Long id,
            @RequestParam String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String website,
            @RequestParam(required = false) String location,
            Authentication auth,
            Model model) {
        try {
            User recruiter = getLoggedInUser(auth);

            Company company = new Company();

            // If id exists → load existing and update
            if (id != null) {
                company = companyService
                    .getCompanyById(id)
                    .orElse(new Company());
            }

            // Set values
            company.setName(name);
            company.setDescription(description);
            company.setWebsite(website);
            company.setLocation(location);
            company.setRecruiter(recruiter);

            // Save — creates new if no id,
            // updates if id exists
            companyService.updateCompany(company);

            return "redirect:/recruiter/companies";

        } catch (Exception e) {
            model.addAttribute("error",
                "Something went wrong: "
                + e.getMessage());
            model.addAttribute("company",
                new Company());
            return "recruiter/company";
        }
    }

    // View all companies
    @GetMapping("/companies")
    public String allCompanies(Model model) {
        model.addAttribute("companies",
            companyService.getAllCompanies());
        return "recruiter/companies";
    }
 // Edit specific company by id
    @GetMapping("/company/{id}/edit")
    public String editCompany(
            @PathVariable Long id,
            Model model) {
        Company company = companyService
            .getCompanyById(id)
            .orElseThrow();
        model.addAttribute("company", company);
        return "recruiter/company";
    }
}