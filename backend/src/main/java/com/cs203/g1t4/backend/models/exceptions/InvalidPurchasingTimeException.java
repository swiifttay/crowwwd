package com.cs203.g1t4.backend.models.exceptions;

public class InvalidPurchasingTimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidPurchasingTimeException(String eventName) {
        super(eventName + " is not open for purchasing tickets");
    }
}
