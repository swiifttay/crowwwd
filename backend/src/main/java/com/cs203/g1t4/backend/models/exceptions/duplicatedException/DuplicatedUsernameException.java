package com.cs203.g1t4.backend.models.exceptions.duplicatedException;

public class DuplicatedUsernameException extends DuplicatedException {

    private static final long serialVersionUID = 1L;

    public DuplicatedUsernameException(String username) {
        super("User " + username + " exists");
    }
}
