package com.hszg.backend.service.edit;

import com.hszg.backend.data.properties.YearProperties;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class YearPropertiesEdit {

    private Integer newYear = null;
    private String newObjectName = null;
    private Boolean newExists = null;
    private Long newModelID = null;
    private Long newTextureID = null;
    private String newStreetName = null;
    private Integer newStreetNumber = null;

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

        var modelID = this.getNewModelID();
        if (modelID != null) {
            target.setModelID(modelID);
        }

        var textureID = this.getNewTextureID();
        if (textureID != null) {
            target.setTextureID(textureID);
        }

        var streetName = this.getNewStreetName();
        if (streetName != null) {
            target.setStreetName(streetName);
        }

        var streetNumber = this.getNewStreetNumber();
        if (streetNumber != null) {
            target.setStreetNumber(streetNumber);
        }

    }

    @Nullable
    public Integer getNewYear() {
        return newYear;
    }

    public void setNewYear(@Nullable Integer newYear) {
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
    public Long getNewModelID() {
        return newModelID;
    }

    public void setNewModelID(@Nullable Long newModelID) {
        this.newModelID = newModelID;
    }

    @Nullable
    public Long getNewTextureID() {
        return newTextureID;
    }

    public void setNewTextureID(@Nullable Long newTextureID) {
        this.newTextureID = newTextureID;
    }

    @Nullable
    public String getNewStreetName() {
        return newStreetName;
    }

    public void setNewStreetName(@Nullable String newStreetName) {
        this.newStreetName = newStreetName;
    }

    @Nullable
    public Integer getNewStreetNumber() {
        return newStreetNumber;
    }

    public void setNewStreetNumber(@Nullable Integer newStreetNumber) {
        this.newStreetNumber = newStreetNumber;
    }
}
