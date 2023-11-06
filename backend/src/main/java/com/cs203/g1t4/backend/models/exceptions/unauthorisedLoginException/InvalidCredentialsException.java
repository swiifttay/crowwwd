package com.cs203.g1t4.backend.models.exceptions.unauthorisedLoginException;

public class InvalidCredentialsException extends UnauthorisedLoginException {

    private static final long serialVersionUID = 1L;

    public InvalidCredentialsException() {
        super("Username or Password is incorrect. Please try again");
    }
}
