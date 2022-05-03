package com.hszg.backend.data.properties;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public interface YearProperties {

    @NotNull
    Integer getYear();
    void setYear(@NotNull Integer year);

    @NotNull
    String getObjectName();
    void setObjectName(@NotNull String name);

    @NotNull
    Boolean getExists();
    void setExists(@NotNull Boolean exists);

    @NotNull
    Long getModelID();
    void setModelID(@NotNull Long modelID);

    @NotNull
    Long getTextureID();
    void setTextureID(@NotNull Long textureID);

    @NotNull
    String getStreetName();
    void setStreetName(@NotNull String streetName);

    @NotNull
    Integer getStreetNumber();
    void setStreetNumber(@NotNull Integer streetNumber);

}
