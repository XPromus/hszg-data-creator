package com.hszg.backend.data.properties;

import javax.validation.constraints.NotNull;

public interface ObjectProperties {

    @NotNull
    String getName();
    void setName(@NotNull String name);

    @NotNull
    Float getLatitude();
    void setLatitude(@NotNull Float latitude);

    @NotNull
    Float getLongitude();
    void setLongitude(@NotNull Float longitude);

    @NotNull
    Long getIdentifierId();
    void setIdentifierId(@NotNull Long identifierId);

    @NotNull
    String getIdentifierResult();
    void setIdentifierResult(@NotNull String identifierResult);

}
