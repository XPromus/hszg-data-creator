package com.hszg.backend.service;

import com.hszg.backend.data.model.Image;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {

    @Value("${upload.path}")
    private String uploadPath;

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(uploadPath));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload folder!");
        }
    }

    public void initObjectFolder(Long objectId) {
        try {
            Files.createDirectories(Paths.get(uploadPath + "/" + objectId));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload folder!");
        }
    }

    public String save(Long objectId, MultipartFile file) {

        try {
            Path root = Paths.get(uploadPath + "/" + objectId);
            if (!Files.exists(root)) {
                initObjectFolder(objectId);
            }
            Path targetLocation = root.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return root.toString();
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }

    }

    public Resource loadFromObject(Image image) {
        try {
            var path = image.getUrl();
            var filename = image.getFilename();
            Path file = Paths.get(path).resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public void deleteFile(String path) {
        FileSystemUtils.deleteRecursively(Paths.get(path).toFile());
    }

    public void deleteAllFromObject(Long objectId) {
        FileSystemUtils.deleteRecursively(Paths.get(uploadPath + "/" + objectId).toFile());
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(Paths.get(uploadPath).toFile());
    }

}
