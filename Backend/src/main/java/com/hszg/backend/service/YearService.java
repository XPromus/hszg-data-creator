package com.hszg.backend.service;

import com.hszg.backend.api.error.ErrorText;
import com.hszg.backend.api.error.ResourceNotFoundException;
import com.hszg.backend.data.model.Object;
import com.hszg.backend.data.model.Year;
import com.hszg.backend.repos.ObjectRepository;
import com.hszg.backend.repos.YearRepository;
import com.hszg.backend.service.edit.YearPropertiesEdit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
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
    public Year createYear(Long objectId, LocalDate year) {

        Optional<Object> object = objectRepository.findById(objectId);
        if (object.isEmpty()) {
            throw new ResourceNotFoundException(ErrorText.getObjectString(objectId));
        }

        Year ret = new Year();
        ret.setYear(year);
        ret.setObject(object.get());

        return yearRepository.save(ret);

    }

    public Year getYearById(Long yearId) {

        Optional<Year> year = yearRepository.findById(yearId);
        if (year.isEmpty()) {
            throw new ResourceNotFoundException(ErrorText.getYearString(yearId));
        }

        return year.get();

    }

    public List<Year> getYearsFromObjectById(Long objectId) {

        Optional<Object> object = objectRepository.findById(objectId);
        if (object.isEmpty()) {
            throw new ResourceNotFoundException(ErrorText.getObjectString(objectId));
        }

        return yearRepository.findYearsByObjectId(objectId);

    }

    public void deleteYearById(Long yearId) {

        Optional<Year> year = yearRepository.findById(yearId);
        if (year.isEmpty()) {
            throw new ResourceNotFoundException(ErrorText.getYearString(yearId));
        }

        yearRepository.deleteById(yearId);

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

}
