package com.hszg.backend.data.properties;

import com.hszg.backend.data.model.Privilege;

import javax.validation.constraints.NotNull;
import java.util.Set;

public interface EditorUserProperties {

    @NotNull
    String getEmail();
    void setEmail(@NotNull String email);

    @NotNull
    String getPassword();
    void setPassword(@NotNull String password);

    @NotNull
    Set<Privilege> getPrivileges();
    void setPrivileges(@NotNull Set<Privilege> privileges);

}
