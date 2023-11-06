package com.cs203.g1t4.backend.models.exceptions.invalidIdException;

public class InvalidTicketIdException extends InvalidIdException {

    private static final long serialVersionUID = 1L;

    public InvalidTicketIdException(String ticketId) {
        super("Ticket with ticketId: " + ticketId + " does not exists");
    }
}
