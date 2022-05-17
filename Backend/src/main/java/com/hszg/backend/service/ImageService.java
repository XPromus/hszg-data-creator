package com.hszg.backend.service;

import com.hszg.backend.api.error.ErrorText;
import com.hszg.backend.api.error.ImageNotFoundException;
import com.hszg.backend.api.error.ObjectNotFoundException;
import com.hszg.backend.api.message.UploadResponseMessage;
import com.hszg.backend.data.model.Image;
import com.hszg.backend.data.model.Object;
import com.hszg.backend.repos.ImageRepository;
import com.hszg.backend.repos.ObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final ObjectRepository objectRepository;
    private final FileService fileService;

    @Autowired
    public ImageService(ImageRepository imageRepository, ObjectRepository objectRepository, FileService fileService) {
        this.imageRepository = imageRepository;
        this.objectRepository = objectRepository;
        this.fileService = fileService;
    }

    @Transactional
    public Image uploadFile(MultipartFile file, Long objectId) {

        var object = checkObjectExistence(objectId);

        Image image = new Image();
        image.setObject(object);
        image.setFilename(file.getOriginalFilename());

        try {
            var path = fileService.save(objectId, file);
            image.setUrl(path);
            return imageRepository.save(image);
        } catch (Exception e) {
            throw new RuntimeException("Upload failed");
        }

    }

    public byte[] getFile(Long id) throws IOException {
        var image = checkImageExistence(id);
        var file = fileService.loadFromObject(image);
        return file.getInputStream().readAllBytes();
        /*
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);

         */
    }

    public List<Image> getImagesFromObject(Long objectId) {
        return imageRepository.findImagesByObjectId(objectId);
    }

    public void deleteAllImagesFromObject(Long objectId) {
        fileService.deleteAllFromObject(objectId);
        var object = checkObjectExistence(objectId).getImages();
        for (Image image : object) {
            imageRepository.delete(image);
        }
    }

    public void deleteImage(Long id) {
        var image = checkImageExistence(id);
        var path = image.getUrl() + "/" + image.getFilename();
        fileService.deleteFile(path);
        imageRepository.delete(image);
    }

    private Image checkImageExistence(Long id) {
        Optional<Image> optionalImage = imageRepository.findById(id);
        if (optionalImage.isEmpty()) {
            throw new ImageNotFoundException(ErrorText.getImageString(id));
        }

        return optionalImage.get();
    }

    private Object checkObjectExistence(Long id) {
        Optional<Object> optionalObject = objectRepository.findById(id);
        if (optionalObject.isEmpty()) {
            throw new ObjectNotFoundException(ErrorText.getObjectString(id));
        }

        return optionalObject.get();
    }

}
