package com.job_portal.servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/upload")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,      // 1MB
    maxFileSize       = 1024 * 1024 * 5,  // 5MB
    maxRequestSize    = 1024 * 1024 * 10  // 10MB
)
public class FileUploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        // Get the uploaded file part
        Part filePart = request.getPart("resume");

        // Get original file name
        String fileName = Paths.get(
            filePart.getSubmittedFileName()).getFileName().toString();

        // Validate — only PDF allowed
        if (!fileName.endsWith(".pdf")) {
            response.sendRedirect("/seeker/profile?error=onlypdf");
            return;
        }

        // Define upload directory
        String uploadDir = getServletContext()
            .getRealPath("") + "static/uploads/";

        // Create directory if not exists
        File uploadFolder = new File(uploadDir);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }

        // Save file
        String savedFileName = System.currentTimeMillis()
            + "_" + fileName;
        filePart.write(uploadDir + savedFileName);

        // Redirect back with success
        response.sendRedirect(
            "/seeker/profile?uploaded=true&file=" + savedFileName);
    }
}