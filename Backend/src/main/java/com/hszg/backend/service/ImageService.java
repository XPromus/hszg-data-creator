package com.hszg.backend.service;

import com.hszg.backend.data.model.Image;
import com.hszg.backend.repos.ImageRepository;
import com.hszg.backend.repos.ObjectRepository;
import com.hszg.backend.service.check.CheckExistence;
import com.hszg.backend.service.edit.ImagePropertiesEdit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final ObjectRepository objectRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository, ObjectRepository objectRepository) {
        this.imageRepository = imageRepository;
        this.objectRepository = objectRepository;
    }

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    @Transactional
    public Image createImage(Long objectId) {

        var object = CheckExistence.checkObjectExistence(objectRepository, objectId);

        Image ret = new Image();
        ret.setObject(object);

        return imageRepository.save(ret);

    }

    public Image getImageById(Long imageId) {
        return CheckExistence.checkImageExistence(imageRepository, imageId);
    }

    public List<Image> getImagesFromObjectsById(Long objectId) {
        CheckExistence.checkObjectExistence(objectRepository, objectId);
        return imageRepository.findImagesByObjectId(objectId);
    }

    public void deleteImageById(Long imageId) {
        var image = CheckExistence.checkImageExistence(imageRepository, imageId);
        imageRepository.delete(image);
    }

    @Transactional
    public Image editImage(Long imageId, ImagePropertiesEdit changes) {
        var image = getImageById(imageId);
        changes.applyChanges(image);
        return imageRepository.save(image);
    }

}
