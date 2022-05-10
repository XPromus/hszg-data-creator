package com.hszg.backend.api.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PrivilegeDoesNotExistException extends RuntimeException {

    public PrivilegeDoesNotExistException(String name) {
        super("Privilege with name " + name + " not found");
    }

    public PrivilegeDoesNotExistException(Long id) {
        super("Privilege with id " + id + " does not exist");
    }

}
