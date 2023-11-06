package com.cs203.g1t4.backend.models.exceptions.invalidFieldsException;

public class InvalidCategoryException extends InvalidFieldsException {

    private static final long serialVersionUID = 1L;

    public InvalidCategoryException(String category) {
        super("Category " + category + " cannot be found. Please try again");
    }
}
