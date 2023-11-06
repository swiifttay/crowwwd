package com.cs203.g1t4.backend.models.exceptions.invalidFieldsException;

public class InvalidImageException extends InvalidFieldsException {

    private static final long serialVersionUID = 1L;

    public InvalidImageException() {
        super("Image could not be saved");
    }
}
