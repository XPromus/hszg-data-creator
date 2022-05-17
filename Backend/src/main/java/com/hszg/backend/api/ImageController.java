package com.hszg.backend.api;

import com.hszg.backend.api.message.UploadResponseMessage;
import com.hszg.backend.data.model.Image;
import com.hszg.backend.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/image")
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("upload/{objectId}")
    public Image uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Long objectId) {
        return imageService.uploadFile(file, objectId);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) throws IOException {
        return new ResponseEntity<>(imageService.getFile(id), HttpHeaders.EMPTY, HttpStatus.OK);
    }

    @GetMapping("/get/object/{objectId}")
    public List<Image> getImagesFromObject(@PathVariable Long objectId) {
        return imageService.getImagesFromObject(objectId);
    }

    @DeleteMapping("/delete/object/{objectId}")
    public void deleteAllImagesFromObject(@PathVariable Long objectId) {
        imageService.deleteAllImagesFromObject(objectId);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteImage(@PathVariable Long id) {
        imageService.deleteImage(id);
    }

}
