package com.hszg.backend.service.edit;

import com.hszg.backend.data.properties.YearProperties;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class YearPropertiesEdit {

    private Integer newYear = null;
    private String newObjectName = null;
    private Boolean newExists = null;
    private String newModelID = null;
    private String newTextureID = null;
    private String newStreetName = null;
    private String newStreetNumber = null;
    private Long newIdentifierId = null;
    private String newIdentifierResult = null;

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

        var identifierId = this.getNewIdentifierId();
        if (identifierId != null) {
            target.setIdentifierId(identifierId);
        }

        var identifierResult = this.getNewIdentifierResult();
        if (identifierResult != null) {
            target.setIdentifierResult(identifierResult);
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
    public String getNewModelID() {
        return newModelID;
    }

    public void setNewModelID(@Nullable String newModelID) {
        this.newModelID = newModelID;
    }

    @Nullable
    public String getNewTextureID() {
        return newTextureID;
    }

    public void setNewTextureID(@Nullable String newTextureID) {
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
    public String getNewStreetNumber() {
        return newStreetNumber;
    }

    public void setNewStreetNumber(@Nullable String newStreetNumber) {
        this.newStreetNumber = newStreetNumber;
    }

    public void setNewIdentifierId(@Nullable Long newIdentifierId) {
        this.newIdentifierId = newIdentifierId;
    }

    @Nullable
    public Long getNewIdentifierId() {
        return newIdentifierId;
    }

    public void setNewIdentifierResult(@Nullable String newIdentifierResult) {
        this.newIdentifierResult = newIdentifierResult;
    }

    @Nullable
    public String getNewIdentifierResult() {
        return newIdentifierResult;
    }

}
