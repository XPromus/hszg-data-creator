package com.hszg.backend.service;

import com.hszg.backend.api.error.ErrorText;
import com.hszg.backend.api.error.ObjectNotFoundException;
import com.hszg.backend.api.error.YearNotFoundException;
import com.hszg.backend.data.model.Object;
import com.hszg.backend.data.model.Year;
import com.hszg.backend.repos.ObjectRepository;
import com.hszg.backend.repos.YearRepository;
import com.hszg.backend.service.edit.YearPropertiesEdit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class YearService {

    public final YearRepository yearRepository;
    public final ObjectRepository objectRepository;

    @Autowired
    public YearService(YearRepository yearRepository, ObjectRepository objectRepository) {
        this.yearRepository = yearRepository;
        this.objectRepository = objectRepository;
    }

    @Transactional
    public Year createYear(Long objectId, Integer year) {

        var object = checkObjectExistence(objectId);

        Year ret = new Year();
        ret.setYear(year);
        ret.setObject(object);

        return yearRepository.save(ret);

    }

    public Year getYearById(Long yearId) {
        return checkYearExistence(yearId);
    }

    public List<Year> getYearsFromObjectById(Long objectId) {
        checkObjectExistence(objectId);
        return yearRepository.findYearsByObjectId(objectId);
    }

    public void deleteYearById(Long yearId) {
        yearRepository.delete(checkYearExistence(yearId));
    }

    @Transactional
    public Year editYear(Long yearId, YearPropertiesEdit changes) {
        var year = getYearById(yearId);
        changes.applyChanges(year);
        return yearRepository.save(year);
    }

    public List<Year> getAllYears() {
        return yearRepository.findAll();
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

}
