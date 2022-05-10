package com.hszg.backend.api.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserDoesNotExistException extends RuntimeException {

    public UserDoesNotExistException(String email) {
        super("EditorUser with email " + email + " does not exist.");
    }

    public UserDoesNotExistException(Long id) {
        super("EditorUser with id " + id + " does not exist");
    }

}
