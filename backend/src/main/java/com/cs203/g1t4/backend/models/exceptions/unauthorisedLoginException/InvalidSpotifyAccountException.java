package com.cs203.g1t4.backend.models.exceptions.unauthorisedLoginException;

public class InvalidSpotifyAccountException extends UnauthorisedLoginException{

    private static final long serialVersionUID = 1L;

    public InvalidSpotifyAccountException() {
        super("Spotify Account Could Not Connect");
    }
}
