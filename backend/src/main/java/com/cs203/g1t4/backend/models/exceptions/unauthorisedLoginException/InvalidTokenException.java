package com.cs203.g1t4.backend.models.exceptions.unauthorisedLoginException;

public class InvalidTokenException extends UnauthorisedLoginException {

    private static final long serialVersionUID = 1L;

    public InvalidTokenException() {
        super("Token is not valid");
    }
}
