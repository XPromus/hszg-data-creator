package com.hszg.backend.api;

import com.hszg.backend.data.model.Year;
import com.hszg.backend.service.YearService;
import com.hszg.backend.service.edit.YearPropertiesEdit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/year")
public class YearController {

    private final YearService yearService;

    @Autowired
    public YearController(YearService yearService) {
        this.yearService = yearService;
    }

    /**
     * * Method was tested (✔)
     * @return Return a list of all years
     */
    @GetMapping("/get/all")
    public List<Year> getAllYears() {
        return yearService.getAllYears();
    }

    /**
     * * Method was tested (✔)
     */
    @PostMapping("/create/{objectId}/{year}")
    public Year createYear(@PathVariable Long objectId, @PathVariable Integer year) {
        return yearService.createYear(objectId, year);
    }

    /**
     * * Method was tested (✔)
     */
    @GetMapping("/get/{yearId}")
    public Year getYearById(@PathVariable Long yearId) {
        return yearService.getYearById(yearId);
    }

    /**
     * * Method was tested (✔)
     */
    @GetMapping("/get/object/{objectId}")
    public List<Year> getYearsFromObjectById(@PathVariable Long objectId) {
        return yearService.getYearsFromObjectById(objectId);
    }

    /**
     * * Method was tested (✔)
     */
    @DeleteMapping("/delete/{yearId}")
    public void deleteYearById(@PathVariable Long yearId) {
        yearService.deleteYearById(yearId);
    }

    /**
     * * Method was tested (✔)
     */
    @PostMapping("/edit/{yearId}")
    public Year editYear(@PathVariable Long yearId, @RequestBody YearPropertiesEdit changes) {
        return yearService.editYear(yearId, changes);
    }

}
