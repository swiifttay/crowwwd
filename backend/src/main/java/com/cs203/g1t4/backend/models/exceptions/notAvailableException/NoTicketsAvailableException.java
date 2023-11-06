package com.cs203.g1t4.backend.models.exceptions.notAvailableException;

public class NoTicketsAvailableException extends NotAvailableException {

    private static final long serialVersionUID = 1L;
    public NoTicketsAvailableException(String eventId) {
        super("There are no more tickets available for " + eventId);
    }
}
