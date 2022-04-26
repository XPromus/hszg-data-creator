package com.hszg.backend.service.edit;

import com.hszg.backend.data.properties.ImageProperties;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

public class ImagePropertiesEdit {

    private String newImagePath = null;
    private String newImageDescription = null;

    public void applyChanges(@NotNull final ImageProperties target) {

        var imagePath = this.getNewImagePath();
        if (imagePath != null) {
            target.setImagePath(imagePath);
        }

        var imageDescription = this.getNewImageDescription();
        if (imageDescription != null) {
            target.setImageDescription(imageDescription);
        }

    }

    @Nullable
    public String getNewImagePath() {
        return newImagePath;
    }

    public void setNewImagePath(@Nullable String newImagePath) {
        this.newImagePath = newImagePath;
    }

    @Nullable
    public String getNewImageDescription() {
        return newImageDescription;
    }

    public void setNewImageDescription(@Nullable String newImageDescription) {
        this.newImageDescription = newImageDescription;
    }

}
