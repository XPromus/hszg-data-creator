package com.hszg.backend.service.edit;

import com.hszg.backend.data.properties.IdentifierProperties;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

public class IdentifierPropertiesEdit {

    private String newIdentifierName;

    public void applyChanges(@NotNull final IdentifierProperties target) {

        var identifierName = this.getNewIdentifierName();
        if (identifierName != null) {
            target.setIdentifierName(identifierName);
        }

    }

    @Nullable
    public String getNewIdentifierName() {
        return newIdentifierName;
    }

}
