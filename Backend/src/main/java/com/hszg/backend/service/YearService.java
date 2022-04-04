package com.hszg.backend.service;

import com.hszg.backend.data.model.Year;
import com.hszg.backend.repos.ObjectRepository;
import com.hszg.backend.repos.YearRepository;
import com.hszg.backend.service.check.CheckExistence;
import com.hszg.backend.service.edit.YearPropertiesEdit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

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

        var object = CheckExistence.checkObjectExistence(objectRepository, objectId);

        Year ret = new Year();
        ret.setYear(year);
        ret.setObject(object);

        return yearRepository.save(ret);

    }

    public Year getYearById(Long yearId) {
        return CheckExistence.checkYearExistence(yearRepository, yearId);
    }

    public List<Year> getYearsFromObjectById(Long objectId) {
        CheckExistence.checkObjectExistence(objectRepository, objectId);
        return yearRepository.findYearsByObjectId(objectId);
    }

    public void deleteYearById(Long yearId) {
        yearRepository.delete(CheckExistence.checkYearExistence(yearRepository, yearId));
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
