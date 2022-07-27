package com.hszg.backend.service;

import com.hszg.backend.api.error.ErrorText;
import com.hszg.backend.api.error.ObjectNotFoundException;
import com.hszg.backend.api.error.YearNotFoundException;
import com.hszg.backend.data.model.Media;
import com.hszg.backend.data.model.Object;
import com.hszg.backend.data.model.Year;
import com.hszg.backend.repos.MediaRepository;
import com.hszg.backend.repos.ObjectRepository;
import com.hszg.backend.repos.YearRepository;
import com.hszg.backend.service.edit.ObjectPropertiesEdit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ObjectService {

    private final ObjectRepository objectRepository;
    private final YearRepository yearRepository;
    private final MediaRepository mediaRepository;

    @Autowired
    public ObjectService(ObjectRepository objectRepository, YearRepository yearRepository, MediaRepository mediaRepository) {
        this.objectRepository = objectRepository;
        this.yearRepository = yearRepository;
        this.mediaRepository = mediaRepository;
    }

    @Transactional
    public Object createObject(Object object) {
        return objectRepository.save(object);
    }

    public List<Object> getObjects() {
        return objectRepository.findAll();
    }

    public Object getObjectById(Long objectId) {
        return checkObjectExistence(objectId);
    }

    public void deleteObject(Long objectId) {

        var object = checkObjectExistence(objectId);
        var years = yearRepository.findYearsByObjectId(objectId);
        var medias = mediaRepository.findMediaByObjectId(objectId);

        for (Year year : years) {
            yearRepository.delete(checkYearExistence(year.getId()));
        }

        for (Media media : medias) {
            mediaRepository.delete(media);
        }

        objectRepository.delete(object);

    }

    @Transactional
    public Object editObject(Long objectId, ObjectPropertiesEdit changes) {
        var object = getObjectById(objectId);
        changes.applyChanges(object);
        return objectRepository.save(object);
    }

    private Object checkObjectExistence(Long id) {
        Optional<Object> optionalObject = objectRepository.findById(id);
        if (optionalObject.isEmpty()) {
            throw new ObjectNotFoundException(ErrorText.getObjectString(id));
        }

        return optionalObject.get();
    }

    private Year checkYearExistence(Long id) {
        Optional<Year> optionalYear = yearRepository.findById(id);
        if (optionalYear.isEmpty()) {
            throw new YearNotFoundException(ErrorText.getYearString(id));
        }

        return optionalYear.get();
    }

    public List<Object> findObjectsByIdentifierId(Long identifierId) {
        return objectRepository.findObjectsByIdentifierId(identifierId);
    }

}
