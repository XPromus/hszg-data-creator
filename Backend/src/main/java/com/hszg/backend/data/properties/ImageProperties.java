package com.hszg.backend.data.properties;

import javax.validation.constraints.NotNull;

public interface ImageProperties {

    @NotNull
    String getImagePath();
    void setImagePath(@NotNull String imagePath);

    @NotNull
    String getImageDescription();
    void setImageDescription(@NotNull String imageDescription);

}
