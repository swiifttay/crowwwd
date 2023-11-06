package com.cs203.g1t4.backend.models.exceptions.duplicatedException;

public class DuplicatedFriendException extends DuplicatedException {

    private static final long serialVersionUID = 1L;

    public DuplicatedFriendException(String friendUsername) {
        super("Friend " + friendUsername + " exists");
    }
}
