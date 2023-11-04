package com.cs203.g1t4.backend.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST) // 400 Error
public class DuplicatedEventException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DuplicatedEventException(String artistId, String eventName) {
        //Might consider needing to check by date instead of eventName as Artist can have multiple events
        //of the same name
        super("Artist: " + artistId + " already has an event " + eventName);
    }
}
