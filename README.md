# 🧑‍💼 Job Recruitment Portal Using Java Full Stack

A full-stack web application built using **Java, Spring Boot, Spring Security, Hibernate, PostgreSQL, Thymeleaf and Bootstrap** that connects Job Seekers with Recruiters.

---

## 📸 Screenshots

https://github.com/user-attachments/assets/9b772c6c-fc94-4539-a56e-44520d2e0cd8

---

## 🚀 Features

### 👤 Job Seeker
- Register and login securely
- Browse and search job listings by keyword, category and location
- View detailed job descriptions
- Apply for jobs with a cover letter
- Upload resume (PDF only, max 5MB)
- Track application status (Applied → Shortlisted → Hired / Rejected)
- View profile with member details

### 🏢 Recruiter
- Register and login securely
- Create and manage company profiles (multiple companies supported)
- Post job listings with full details (title, location, salary, category, type, experience)
- View all applicants for each job
- Update application status (Shortlist / Hire / Reject)
- Close job listings when filled
- View all registered companies


---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Backend Framework | Spring Boot 4.x |
| Web Layer | Spring MVC |
| Security | Spring Security + BCrypt |
| ORM | Hibernate / JPA |
| Database Queries | Spring Data JPA + JDBC |
| Database | PostgreSQL |
| Frontend | Thymeleaf + Bootstrap 5 |
| File Upload | Java Servlet (MultipartConfig) |
| REST API | Spring REST Controller |
| Connection Pool | HikariCP |
| Build Tool | Maven |
| Language | Java 17 |

---

## 📁 Project Structure

```
src/main/java/com/job_portal/
 ├── controller/
 │    ├── AuthController.java
 │    ├── SeekerController.java
 │    ├── RecruiterController.java
 │    ├── AdminController.java
 │    └── JobApiController.java
 ├── model/
 │    ├── User.java
 │    ├── Company.java
 │    ├── Job.java
 │    └── Application.java
 ├── repository/
 │    ├── UserRepository.java
 │    ├── CompanyRepository.java
 │    ├── JobRepository.java
 │    └── ApplicationRepository.java
 ├── service/
 │    ├── UserService.java
 │    ├── CompanyService.java
 │    ├── JobService.java
 │    └── ApplicationService.java
 ├── config/
 │    ├── SecurityConfig.java
 │    ├── CustomUserDetailsService.java
 │    └── CustomSuccessHandler.java
 ├── servlet/
 │    └── FileUploadServlet.java
 └── util/
      ├── FileUploadUtil.java
      ├── EmailUtil.java
      └── SecurityUtil.java

src/main/resources/
 ├── templates/
 │    ├── index.html
 │    ├── auth/
 │    │    ├── login.html
 │    │    └── register.html
 │    ├── seeker/
 │    │    ├── dashboard.html
 │    │    ├── jobList.html
 │    │    ├── jobDetails.html
 │    │    └── profile.html
 │    ├── recruiter/
 │    │    ├── dashboard.html
 │    │    ├── postJob.html
 │    │    ├── applicants.html
 │    │    ├── company.html
 │    │    └── companies.html
 │    └── admin/
 │         ├── dashboard.html
 │         └── jobs.html
 ├── static/
 │    ├── css/style.css
 │    ├── js/main.js
 │    └── uploads/
 └── application.properties
```

---

## 🗄️ Database Design

### Tables

```
users
├── id (PK)
├── full_name
├── email (unique)
├── password (BCrypt encrypted)
├── role (SEEKER / RECRUITER / ADMIN)
├── phone
├── resume_path
└── created_at

companies
├── id (PK)
├── name
├── description
├── website
├── location
├── logo_path
└── recruiter_id (FK → users)

jobs
├── id (PK)
├── title
├── description
├── location
├── salary
├── category
├── job_type
├── experience
├── status (OPEN / CLOSED)
├── posted_at
├── company_id (FK → companies)
└── recruiter_id (FK → users)

applications
├── id (PK)
├── cover_letter
├── status (APPLIED / SHORTLISTED / REJECTED / HIRED)
├── applied_at
├── updated_at
├── seeker_id (FK → users)
└── job_id (FK → jobs)
```

### Relationships
```
users (1) ──── (many) companies
companies (1) ──── (many) jobs
users (1) ──── (many) jobs (as recruiter)
users (many) ──── (many) jobs (through applications)
```

---

## ⚙️ Setup and Installation

### Prerequisites
- Java 17 or higher
- Maven 3.x
- PostgreSQL 13 or higher
- Eclipse IDE or IntelliJ IDEA

### Step 1 — Clone the Repository
```bash
git clone https://github.com/yourusername/job-recruitment-portal.git
cd job-recruitment-portal
```

### Step 2 — Create PostgreSQL Database
```sql
CREATE DATABASE jobportal_db;
```

### Step 3 — Configure `application.properties`
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/jobportal_db
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html
spring.servlet.multipart.max-file-size=5MB
spring.servlet.multipart.max-request-size=5MB
server.port=8080
```


### Step 4 — Run the Application
```bash
mvn spring-boot:run
```
Or run `JobPortalApplication.java` directly from your IDE.

### Step 5 — Open in Browser
```
http://localhost:8080
```

---

## 👥 User Roles and Access

| Role | Access |
|---|---|
| Job Seeker | Browse jobs, apply, upload resume, track applications |
| Recruiter | Post jobs, manage companies, view and update applicants |
| Admin | Manage all users and job listings |

---

## 🔐 Security

- Passwords encrypted using **BCrypt**
- Role-based access control with **Spring Security**
- CSRF protection enabled on all forms
- Logout requires **POST request** (prevents CSRF attacks)
- Admin account created directly in DB (no public registration)

---

## 🌊 Application Flow

```
Job Seeker:
Register → Login → Browse Jobs → Apply → Track Status

Recruiter:
Register → Login → Create Company → Post Job → View Applicants → Update Status

Admin:
Login (DB only) → Manage Users → Manage Jobs
```

---

## 💡 Key Design Decisions

| Decision | Reason |
|---|---|
| Used both Hibernate and JDBC | Hibernate for standard CRUD, JDBC for complex search queries |
| BCrypt password encryption | Industry standard one-way hashing |
| Role-based dashboards | Each role has specific functionality |
| REST API alongside MVC | Supports future mobile app integration |
| FileUploadServlet for resume | Raw Servlet gives more control over multipart handling |
| HikariCP connection pool | Prevents too many database connections |

---

## 📝 Future Enhancements

- [ ] Email notifications when application status changes
- [ ] Advanced search with multiple filters
- [ ] Job recommendations based on seeker profile
- [ ] Resume parser
- [ ] Company ratings and reviews
- [ ] Deploy to cloud (Railway / Render)

---
