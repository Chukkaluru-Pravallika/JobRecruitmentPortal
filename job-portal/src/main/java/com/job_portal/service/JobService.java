package com.job_portal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.job_portal.model.Job;
import com.job_portal.model.Job.JobStatus;
import com.job_portal.model.User;
import com.job_portal.repository.JobRepository;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    // Post a new job
    public Job postJob(Job job) {
        return jobRepository.save(job);
    }

    // Get all open jobs (seeker view)
    public List<Job> getAllOpenJobs() {
        return jobRepository.findByStatus(JobStatus.OPEN);
    }

    // Get jobs by recruiter
    public List<Job> getJobsByRecruiter(User recruiter) {
        return jobRepository.findByRecruiter(recruiter);
    }

    // Search jobs by keyword
    public List<Job> searchJobs(String keyword) {
        return jobRepository.searchJobs(keyword);
    }

    // Filter by category
    public List<Job> filterByCategory(String category) {
        return jobRepository.findByCategoryAndStatus(category, JobStatus.OPEN);
    }

    // Filter by location
    public List<Job> filterByLocation(String location) {
        return jobRepository.findByLocationContainingIgnoreCaseAndStatus(
            location, JobStatus.OPEN);
    }

    // Get job by id
    public Optional<Job> getJobById(Long id) {
        return jobRepository.findById(id);
    }

    // Close a job
    public Job closeJob(Long id) {
        Job job = jobRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Job not found!"));
        job.setStatus(JobStatus.CLOSED);
        return jobRepository.save(job);
    }

    // Delete a job
    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }

    // Update job
    public Job updateJob(Job job) {
        return jobRepository.save(job);
    }
}