package com.hszg.backend.data.properties;

import javax.validation.constraints.NotNull;

public interface IdentifierProperties {

    @NotNull
    String getIdentifierName();
    void setIdentifierName(@NotNull String identifierName);

}
