package com.job_portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.job_portal.model.Job;
import com.job_portal.model.Job.JobStatus;
import com.job_portal.model.User;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    // All open jobs (for seekers)
    List<Job> findByStatus(JobStatus status);

    // Jobs posted by a specific recruiter
    List<Job> findByRecruiter(User recruiter);

    // Search jobs by keyword (title or location)
    @Query("SELECT j FROM Job j WHERE " +
           "(LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(j.location) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
           "AND j.status = 'OPEN'")
    List<Job> searchJobs(@Param("keyword") String keyword);

    // Filter by category
    List<Job> findByCategoryAndStatus(String category, JobStatus status);

    // Filter by location
    List<Job> findByLocationContainingIgnoreCaseAndStatus(
        String location, JobStatus status);
}