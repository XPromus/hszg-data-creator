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
    String getModelID();
    void setModelID(@NotNull String modelID);

    @NotNull
    String getTextureID();
    void setTextureID(@NotNull String textureID);

    @NotNull
    String getStreetName();
    void setStreetName(@NotNull String streetName);

    @NotNull
    String getStreetNumber();
    void setStreetNumber(@NotNull String streetNumber);

    @NotNull
    Long getIdentifierId();
    void setIdentifierId(@NotNull Long identifierId);

    @NotNull
    String getIdentifierResult();
    void setIdentifierResult(@NotNull String identifierResult);

}
