package com.job_portal.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender mailSender;

    // Send simple email
    public void sendEmail(String to,
                          String subject,
                          String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            message.setFrom("noreply@jobportal.com");
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Email sending failed: "
                + e.getMessage());
        }
    }

    // Send application status update email
    public void sendStatusUpdateEmail(String to,
                                      String seekerName,
                                      String jobTitle,
                                      String status) {
        String subject = "Application Update - " + jobTitle;
        String body = "Dear " + seekerName + ",\n\n"
            + "Your application for the position of "
            + jobTitle + " has been updated.\n\n"
            + "Current Status: " + status + "\n\n"
            + "Login to JobPortal to view more details.\n\n"
            + "Best regards,\n"
            + "JobPortal Team";
        sendEmail(to, subject, body);
    }

    // Send welcome email on registration
    public void sendWelcomeEmail(String to, String name) {
        String subject = "Welcome to JobPortal!";
        String body = "Dear " + name + ",\n\n"
            + "Welcome to JobPortal! "
            + "Your account has been created successfully.\n\n"
            + "Start exploring thousands of job opportunities.\n\n"
            + "Best regards,\n"
            + "JobPortal Team";
        sendEmail(to, subject, body);
    }
}