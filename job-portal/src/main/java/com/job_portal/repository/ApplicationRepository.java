package com.job_portal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.job_portal.model.Application;
import com.job_portal.model.Job;
import com.job_portal.model.User;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    // All applications by a seeker
    List<Application> findBySeeker(User seeker);

    // All applications for a job (recruiter view)
    List<Application> findByJob(Job job);

    // Check if seeker already applied for a job
    boolean existsBySeekerAndJob(User seeker, Job job);

    // Find specific application
    Optional<Application> findBySeekerAndJob(User seeker, Job job);

    // Count applications per job
    long countByJob(Job job);
}