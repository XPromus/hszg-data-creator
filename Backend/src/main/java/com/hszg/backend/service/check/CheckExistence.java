package com.hszg.backend.service.check;

import com.hszg.backend.api.error.ErrorText;
import com.hszg.backend.api.error.ObjectNotFoundException;
import com.hszg.backend.api.error.YearNotFoundException;
import com.hszg.backend.data.model.Object;
import com.hszg.backend.data.model.Year;
import com.hszg.backend.repos.ObjectRepository;
import com.hszg.backend.repos.YearRepository;

import java.util.Optional;

public class CheckExistence {

    private CheckExistence() {

    }

    public static Object checkObjectExistence(ObjectRepository objectRepository, Long id) {

        Optional<Object> optionalObject = objectRepository.findById(id);
        if (optionalObject.isEmpty()) {
            throw new ObjectNotFoundException(ErrorText.getObjectString(id));
        }

        return optionalObject.get();

    }

    public static Year checkYearExistence(YearRepository yearRepository, Long id) {

        Optional<Year> optionalYear = yearRepository.findById(id);
        if (optionalYear.isEmpty()) {
            throw new YearNotFoundException(ErrorText.getYearString(id));
        }

        return optionalYear.get();

    }

}
