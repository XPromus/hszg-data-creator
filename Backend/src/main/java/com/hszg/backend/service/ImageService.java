package com.hszg.backend.service;

import com.hszg.backend.api.error.ErrorText;
import com.hszg.backend.api.error.ImageNotFoundException;
import com.hszg.backend.api.error.ObjectNotFoundException;
import com.hszg.backend.data.model.Image;
import com.hszg.backend.data.model.Object;
import com.hszg.backend.repos.ImageRepository;
import com.hszg.backend.repos.ObjectRepository;
import com.hszg.backend.service.edit.ImagePropertiesEdit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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

        var object = checkObjectExistence(objectId);

        Image ret = new Image();
        ret.setObject(object);

        return imageRepository.save(ret);

    }

    public Image getImageById(Long imageId) {
        return checkImageExistence(imageId);
    }

    public List<Image> getImagesFromObjectsById(Long objectId) {
        checkObjectExistence(objectId);
        return imageRepository.findImagesByObjectId(objectId);
    }

    public void deleteImageById(Long imageId) {
        var image = checkImageExistence(imageId);
        imageRepository.delete(image);
    }

    @Transactional
    public Image editImage(Long imageId, ImagePropertiesEdit changes) {
        var image = getImageById(imageId);
        changes.applyChanges(image);
        return imageRepository.save(image);
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
