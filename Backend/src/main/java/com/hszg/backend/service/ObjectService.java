package com.hszg.backend.service;

import com.hszg.backend.api.error.ErrorText;
import com.hszg.backend.api.error.ResourceNotFoundException;
import com.hszg.backend.data.model.Object;
import com.hszg.backend.data.model.Year;
import com.hszg.backend.repos.ObjectRepository;
import com.hszg.backend.repos.YearRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ObjectService {

    private final ObjectRepository objectRepository;
    private final YearRepository yearRepository;

    @Autowired
    public ObjectService(ObjectRepository objectRepository, YearRepository yearRepository) {
        this.objectRepository = objectRepository;
        this.yearRepository = yearRepository;
    }

    @Transactional
    public Object createObject(Object object) {
        return objectRepository.save(object);
    }

    public List<Object> getObjects() {
        return objectRepository.findAll();
    }

    public Object getObjectById(Long objectId) {

        Optional<Object> object = objectRepository.findById(objectId);
        if (object.isEmpty()) {
            throw new ResourceNotFoundException(ErrorText.getObjectString(objectId));
        }

        return object.get();

    }

    public void deleteObject(Long objectId) {

        Optional<Object> object = objectRepository.findById(objectId);
        if (object.isEmpty()) {
            throw new ResourceNotFoundException(ErrorText.getObjectString(objectId));
        }

        var years = yearRepository.findYearsByObjectId(objectId);

        for (Year year : years) {
            yearRepository.deleteById(year.getId());
        }

        objectRepository.deleteById(objectId);

    }

}
