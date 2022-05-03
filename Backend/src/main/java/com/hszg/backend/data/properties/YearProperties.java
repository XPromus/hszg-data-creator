package com.hszg.backend.data.properties;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public interface YearProperties {

    @NotNull
    LocalDate getYear();
    void setYear(@NotNull LocalDate year);

    @NotNull
    String getObjectName();
    void setObjectName(@NotNull String name);

    @NotNull
    Boolean getExists();
    void setExists(@NotNull Boolean exists);



}
