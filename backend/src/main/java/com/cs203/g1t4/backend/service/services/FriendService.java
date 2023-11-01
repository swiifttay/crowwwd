package com.cs203.g1t4.backend.service.services;

import com.cs203.g1t4.backend.data.request.friend.FriendRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.models.exceptions.*;
import org.springframework.stereotype.Service;

@Service
public interface FriendService {

    Response addFriend(FriendRequest friendRequest, String username)
            throws DuplicatedFriendException, FriendNotFoundException, InvalidTokenException;

    Response deleteFriend(FriendRequest friendRequest, String username)
            throws InvalidTokenException, InvalidUserIdException;

    Response approveFriend(FriendRequest friendRequest, String username);

    Response getAllFriends(String username);

    Response getApprovedFriends(String username);

    Response getPendingFriends(String username);

}
