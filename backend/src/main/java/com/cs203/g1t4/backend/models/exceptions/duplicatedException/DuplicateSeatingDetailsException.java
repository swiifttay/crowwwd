package com.cs203.g1t4.backend.models.exceptions.duplicatedException;

public class DuplicateSeatingDetailsException extends DuplicatedException {

    private static final long serialVersionUID = 1L;

    public DuplicateSeatingDetailsException(String eventId) {
        super("Seating details for eventId: " + eventId + " already exists");
    }
}
