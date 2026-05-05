package com.job_portal.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {

    // Save file to a directory and return saved file name
    public static String saveFile(
            String uploadDir,
            String fileName,
            MultipartFile file) throws IOException {

        Path uploadPath = Paths.get(uploadDir);

        // Create directory if not exists
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate unique file name
        String uniqueFileName = System.currentTimeMillis()
            + "_" + fileName;

        // Save file
        Path filePath = uploadPath.resolve(uniqueFileName);
        Files.copy(file.getInputStream(),
            filePath, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFileName;
    }

    // Delete a file
    public static void deleteFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            Files.deleteIfExists(path);
        } catch (IOException e) {
            System.err.println("Could not delete file: "
                + e.getMessage());
        }
    }

    // Validate file type
    public static boolean isPdf(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        return fileName != null
            && fileName.toLowerCase().endsWith(".pdf");
    }

    // Validate file size (max 5MB)
    public static boolean isValidSize(MultipartFile file) {
        return file.getSize() <= 5 * 1024 * 1024;
    }
}