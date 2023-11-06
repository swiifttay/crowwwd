package com.cs203.g1t4.backend.models.exceptions.unauthorisedLoginException;

public class PasswordDoNotMatchException extends UnauthorisedLoginException {

    private static final long serialVersionUID = 1L;

    public PasswordDoNotMatchException() {
        super("Passwords do not match");
    }
}
