package com.cs203.g1t4.backend.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST) // 400 Error
public class InvalidTicketIdException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidTicketIdException(String ticketId) {
        super("Ticket with ticketId: " + ticketId + " does not exists");
    }
}
