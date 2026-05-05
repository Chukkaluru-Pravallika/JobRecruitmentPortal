package com.job_portal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    private String website;

    private String location;

    private String logoPath;

    // Many companies can belong to one recruiter
    @ManyToOne
    @JoinColumn(name = "recruiter_id",
                nullable = false)
    @JsonIgnoreProperties({"password",
        "hibernateLazyInitializer"})
    private User recruiter;

    // ---- Getters and Setters ----
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() { return website; }
    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLocation() { return location; }
    public void setLocation(String location) {
        this.location = location;
    }

    public String getLogoPath() { return logoPath; }
    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public User getRecruiter() { return recruiter; }
    public void setRecruiter(User recruiter) {
        this.recruiter = recruiter;
    }
}