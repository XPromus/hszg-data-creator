package com.hszg.backend.service.edit;

import com.hszg.backend.data.properties.MediaProperties;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

public class MediaPropertiesEdit {

    private String newUrl = null;

    public void applyChanges(@NotNull final MediaProperties target) {
        var url = this.getNewUrl();
        if (url != null) {
            target.setUrl(url);
        }
    }

    @Nullable
    public String getNewUrl() {
        return newUrl;
    }

    public void setNewUrl(@Nullable String newUrl) {
        this.newUrl = newUrl;
    }
}
