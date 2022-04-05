package com.hszg.backend.service;

import com.hszg.backend.data.model.Object;
import com.hszg.backend.data.model.Year;
import com.hszg.backend.repos.ObjectRepository;
import com.hszg.backend.repos.YearRepository;
import com.hszg.backend.service.check.CheckExistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
        return CheckExistence.checkObjectExistence(objectRepository, objectId);
    }

    public void deleteObject(Long objectId) {

        var object = CheckExistence.checkObjectExistence(objectRepository, objectId);
        var years = yearRepository.findYearsByObjectId(objectId);

        for (Year year : years) {
            yearRepository.delete(CheckExistence.checkYearExistence(yearRepository, year.getId()));
        }

        objectRepository.delete(object);

    }

}
