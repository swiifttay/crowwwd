package com.cs203.g1t4.backend.models.exceptions.duplicatedException;

public class DuplicatedEventException extends DuplicatedException {

    private static final long serialVersionUID = 1L;

    public DuplicatedEventException(String artistId, String eventName) {
        //Might consider needing to check by date instead of eventName as Artist can have multiple events
        //of the same name
        super("Artist: " + artistId + "already has an event " + eventName + " exists");
    }
}
