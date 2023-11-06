package com.cs203.g1t4.backend.models.exceptions.invalidIdException;

public class InvalidSeatingDetailsException extends InvalidIdException {

    private static final long serialVersionUID = 1L;

    public InvalidSeatingDetailsException(String eventId) {
        super("Seating details for eventId: " + eventId + " does not exists");
    }
}
