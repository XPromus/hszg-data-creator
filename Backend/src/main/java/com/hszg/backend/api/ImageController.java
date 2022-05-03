package com.hszg.backend.api;

import com.hszg.backend.data.model.Image;
import com.hszg.backend.service.ImageService;
import com.hszg.backend.service.edit.ImagePropertiesEdit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * * Method was tested (✔)
     */
    @GetMapping("/get/all")
    public List<Image> getAllImages() {
        return imageService.getAllImages();
    }

    /**
     * * Method was tested (✔)
     */
    @PostMapping("/create/{objectId}")
    public Image createImage(@PathVariable Long objectId) {
        return imageService.createImage(objectId);
    }

    /**
     * * Method was tested (✔)
     */
    @GetMapping("/get/{imageId}")
    public Image getImageById(@PathVariable Long imageId) {
        return imageService.getImageById(imageId);
    }

    /**
     * * Method was tested (✔)
     */
    @GetMapping("/get/object/{objectId}")
    public List<Image> getImagesFromObjectById(@PathVariable Long objectId) {
        return imageService.getImagesFromObjectsById(objectId);
    }

    /**
     * * Method was tested (✔)
     */
    @DeleteMapping("/delete/{imageId}")
    public void deleteImageById(@PathVariable Long imageId) {
        imageService.deleteImageById(imageId);
    }

    /**
     * * Method was tested (✔)
     */
    @PostMapping("/edit/{imageId}")
    public Image editImage(@PathVariable Long imageId, @RequestBody ImagePropertiesEdit changes) {
        return imageService.editImage(imageId, changes);
    }

}
