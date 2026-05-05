package com.job_portal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.job_portal.model.Application;
import com.job_portal.model.Application.ApplicationStatus;
import com.job_portal.model.Job;
import com.job_portal.model.User;
import com.job_portal.repository.ApplicationRepository;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    // Apply for a job
    public Application applyForJob(User seeker, Job job, String coverLetter) {
        // Check if already applied
        if (applicationRepository.existsBySeekerAndJob(seeker, job)) {
            throw new RuntimeException("You have already applied for this job!");
        }
        Application application = new Application();
        application.setSeeker(seeker);
        application.setJob(job);
        application.setCoverLetter(coverLetter);
        return applicationRepository.save(application);
    }

    // Get all applications by seeker
    public List<Application> getApplicationsBySeeker(User seeker) {
        return applicationRepository.findBySeeker(seeker);
    }

    // Get all applications for a job (recruiter view)
    public List<Application> getApplicationsByJob(Job job) {
        return applicationRepository.findByJob(job);
    }

    // Update application status (recruiter action)
    public Application updateStatus(Long applicationId, ApplicationStatus status) {
        Application application = applicationRepository.findById(applicationId)
            .orElseThrow(() -> new RuntimeException("Application not found!"));
        application.setStatus(status);
        return applicationRepository.save(application);
    }

    // Count applications for a job
    public long countApplications(Job job) {
        return applicationRepository.countByJob(job);
    }

    // Check if seeker already applied
    public boolean hasApplied(User seeker, Job job) {
        return applicationRepository.existsBySeekerAndJob(seeker, job);
    }
}