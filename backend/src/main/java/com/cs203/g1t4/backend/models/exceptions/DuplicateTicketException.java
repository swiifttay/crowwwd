package com.cs203.g1t4.backend.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST) // 400 Error
public class DuplicateTicketException extends DuplicatedException {

    private static final long serialVersionUID = 1L;

    public DuplicateTicketException(String eventId, String userAttendingId) {
        super("User " + userAttendingId + " already bought a ticket for " + eventId + " event.");
    }
}
