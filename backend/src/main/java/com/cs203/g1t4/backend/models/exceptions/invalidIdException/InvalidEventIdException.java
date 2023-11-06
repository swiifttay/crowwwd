package com.cs203.g1t4.backend.models.exceptions.invalidIdException;

public class InvalidEventIdException extends InvalidIdException {

    private static final long serialVersionUID = 1L;

    public InvalidEventIdException(String eventId) {
        super("Event with eventId: " + eventId + " does not exists");
    }
}
