package com.hszg.backend.api.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends ResourceNotFoundException {

    public ObjectNotFoundException(String message) {
        super(message);
    }

}
