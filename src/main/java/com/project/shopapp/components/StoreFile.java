package com.project.shopapp.components;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;
@Component
public class StoreFile {
    public String saveImage(MultipartFile file, String folder) throws IOException {
        if (!isImageFile(file) || file.getOriginalFilename() == null) {
            throw new IOException("Invalid image format");
        }
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        // Unique name image
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
        // path save file
        java.nio.file.Path uploadDir = Paths.get(folder);
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        // path to file
        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFileName);
        // copy file
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }
    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }
}
