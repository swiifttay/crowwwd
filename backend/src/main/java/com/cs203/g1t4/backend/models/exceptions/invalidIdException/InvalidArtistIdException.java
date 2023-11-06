package com.cs203.g1t4.backend.models.exceptions.invalidIdException;

public class InvalidArtistIdException extends InvalidIdException {

    private static final long serialVersionUID = 1L;

    public InvalidArtistIdException(String artistId) {
        super("Artist with artistId: " + artistId + " does not exists");
    }
}
