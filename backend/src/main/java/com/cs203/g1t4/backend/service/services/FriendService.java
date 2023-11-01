package com.cs203.g1t4.backend.service;

import com.cs203.g1t4.backend.data.request.friend.FriendRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.models.exceptions.*;
import org.springframework.stereotype.Service;

@Service
public interface FriendService {

    public Response addFriend(FriendRequest friendRequest, String username)
            throws DuplicatedFriendException, FriendNotFoundException, InvalidTokenException;

    public Response deleteFriend(FriendRequest friendRequest, String username)
            throws InvalidTokenException, InvalidUserIdException;

    public Response approveFriend(FriendRequest friendRequest, String username);

    public Response getAllFriends(String username);

    public Response getApprovedFriends(String username);

    public Response getPendingFriends(String username);

}
