package com.cs203.g1t4.backend.models.exceptions.invalidFieldsException;

public class FriendNotFoundException extends InvalidFieldsException {

    private static final long serialVersionUID = 1L;

    public FriendNotFoundException(String username, String friendUsername) {
        super(friendUsername + " does not exists in " + username + "'s friend list");
    }
}
