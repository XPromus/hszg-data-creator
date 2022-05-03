package com.hszg.backend.service.edit;

import com.hszg.backend.data.properties.ObjectProperties;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

public class ObjectPropertiesEdit {

    private String newName;
    private Float newLatitude;
    private Float newLongitude;

    public void applyChanges(@NotNull final ObjectProperties target) {
    
        var name = this.getNewName();
        if (name != null) {
            target.setName(name);
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
    public String getNewName() {
        return newName;
    }

    public void setNewName(@Nullable String newName) {
        this.newName = newName;
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
