package com.cs203.g1t4.backend.models.exceptions.invalidIdException;

public class InvalidVenueException extends InvalidIdException {

    private static final long serialVersionUID = 1L;

    public InvalidVenueException() {
        super("Invalid venue id");
    }
  
}
