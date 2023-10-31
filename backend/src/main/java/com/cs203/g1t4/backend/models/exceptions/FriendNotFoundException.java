package com.cs203.g1t4.backend.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) // 404 Error
public class FriendNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FriendNotFoundException(String username, String friendUsername) {
        super(friendUsername + " does not exists in " + username + "'s friend list");
    }
}
