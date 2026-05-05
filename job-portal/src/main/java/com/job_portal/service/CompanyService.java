package com.job_portal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation
    .Autowired;
import org.springframework.stereotype.Service;

import com.job_portal.model.Company;
import com.job_portal.model.User;
import com.job_portal.repository.CompanyRepository;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    // Always create new company
    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    // Update existing company
    public Company updateCompany(Company company) {
        return companyRepository.save(company);
    }

    // Get first company by recruiter
    public Optional<Company> getCompanyByRecruiter(
            User recruiter) {
        return companyRepository
            .findByRecruiter(recruiter);
    }

    // Get ALL companies by recruiter
    public List<Company> getCompaniesByRecruiter(
            User recruiter) {
        return companyRepository
            .findAllByRecruiter(recruiter);
    }

    // Get company by id
    public Optional<Company> getCompanyById(
            Long id) {
        return companyRepository.findById(id);
    }

    // Get all companies
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    // Delete company
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }

    // Check if recruiter has company
    public boolean hasCompany(User recruiter) {
        return companyRepository
            .existsByRecruiter(recruiter);
    }
}