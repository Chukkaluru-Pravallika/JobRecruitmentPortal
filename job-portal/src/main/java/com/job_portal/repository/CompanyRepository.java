package com.job_portal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository
    .JpaRepository;
import org.springframework.stereotype.Repository;

import com.job_portal.model.Company;
import com.job_portal.model.User;

@Repository
public interface CompanyRepository
        extends JpaRepository<Company, Long> {

    // Get first company by recruiter
    Optional<Company> findByRecruiter(User recruiter);

    // Get ALL companies by recruiter
    List<Company> findAllByRecruiter(User recruiter);

    // Check if recruiter has any company
    boolean existsByRecruiter(User recruiter);
}