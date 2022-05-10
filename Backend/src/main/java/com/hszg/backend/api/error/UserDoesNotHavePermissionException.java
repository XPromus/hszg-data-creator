package com.hszg.backend.api.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserDoesNotHavePermissionException extends RuntimeException {

    public UserDoesNotHavePermissionException(String message) {
        super(message);
    }

}

