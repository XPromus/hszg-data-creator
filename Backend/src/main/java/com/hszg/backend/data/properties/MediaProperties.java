package com.hszg.backend.data.properties;

import javax.validation.constraints.NotNull;

public interface MediaProperties {

    @NotNull
    String getUrl();
    void setUrl(@NotNull String url);

}
