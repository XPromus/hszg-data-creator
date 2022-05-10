package com.hszg.backend.service.edit;

import com.hszg.backend.data.properties.EditorUserProperties;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

public class UserPropertiesEdit {

    private String newEmail;
    private String newPassword;

    public void applyChanges(@NotNull final EditorUserProperties target) {

        var email = this.getNewEmail();
        if (email != null) {
            target.setEmail(email);
        }

        var password = this.getNewPassword();
        if (password != null) {
            target.setPassword(password);
        }

    }

    @Nullable
    public String getNewEmail() {
        return newEmail;
    }

    @Nullable
    public String getNewPassword() {
        return newPassword;
    }

}
