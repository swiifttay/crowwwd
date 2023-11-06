package com.cs203.g1t4.backend.models.exceptions.invalidFieldsException;

public class MissingFieldsException extends InvalidFieldsException {

    private static final long serialVersionUID = 1L;

    public MissingFieldsException() {
        super("Missing Fields exists");
    }
}
