package com.hszg.backend.service.edit;

import com.hszg.backend.data.properties.YearProperties;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class YearPropertiesEdit {

    private LocalDate newYear = null;
    private String newObjectName = null;
    private Boolean newExists = null;

    private Float newLatitude = null;
    private Float newLongitude = null;

    public void applyChanges(@NotNull final YearProperties target) {

        var year = this.getNewYear();
        if (year != null) {
            target.setYear(year);
        }

        var objectName = this.getNewObjectName();
        if (objectName != null) {
            target.setObjectName(objectName);
        }

        var exists = this.getNewExists();
        if (exists != null) {
            target.setExists(exists);
        }

        var latitude = this.getNewLatitude();
        if (latitude != null) {
            target.setLatitude(latitude);
        }

        var longitude = this.getNewLongitude();
        if (longitude != null) {
            target.setLongitude(longitude);
        }

    }

    @Nullable
    public LocalDate getNewYear() {
        return newYear;
    }

    public void setNewYear(@Nullable LocalDate newYear) {
        this.newYear = newYear;
    }

    @Nullable
    public String getNewObjectName() {
        return newObjectName;
    }

    public void setNewObjectName(@Nullable String newName) {
        this.newObjectName = newName;
    }

    @Nullable
    public Boolean getNewExists() {
        return newExists;
    }

    public void setNewExists(@Nullable Boolean newExists) {
        this.newExists = newExists;
    }

    @Nullable
    public Float getNewLatitude() {
        return newLatitude;
    }

    public void setNewLatitude(@Nullable Float newLatitude) {
        this.newLatitude = newLatitude;
    }

    @Nullable
    public Float getNewLongitude() {
        return newLongitude;
    }

    public void setNewLongitude(@Nullable Float newLongitude) {
        this.newLongitude = newLongitude;
    }

}
