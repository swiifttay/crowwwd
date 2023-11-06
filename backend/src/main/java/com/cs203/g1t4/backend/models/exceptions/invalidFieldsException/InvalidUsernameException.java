package com.cs203.g1t4.backend.models.exceptions.invalidFieldsException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) // 404 Error
public class InvalidUsernameException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidUsernameException(String username) {
        super(username + " does not exist");
    }
}
