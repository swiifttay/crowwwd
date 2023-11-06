package com.cs203.g1t4.backend.models.exceptions.duplicatedException;

public class DuplicateFanRecordException extends DuplicatedException{
    private static final long serialVersionUID = 1L;

    public DuplicateFanRecordException(String artistId) {
        super("User is a fan of artist with " + artistId + " already");
    }
}
