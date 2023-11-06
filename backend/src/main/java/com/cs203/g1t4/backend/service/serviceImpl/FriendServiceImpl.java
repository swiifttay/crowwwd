package com.cs203.g1t4.backend.service.serviceImpl;

import com.cs203.g1t4.backend.data.request.friend.FriendRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.friend.FriendResponse;
import com.cs203.g1t4.backend.data.response.friend.SingleFriendResponse;
import com.cs203.g1t4.backend.models.Friend;
import com.cs203.g1t4.backend.models.OutputFriend;
import com.cs203.g1t4.backend.models.User;
import com.cs203.g1t4.backend.models.exceptions.*;
import com.cs203.g1t4.backend.repository.FriendRepository;
import com.cs203.g1t4.backend.repository.UserRepository;
import com.cs203.g1t4.backend.service.services.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    public Response addFriend(FriendRequest friendRequest, String username)
            throws DuplicatedFriendException, FriendNotFoundException, InvalidTokenException {

        //Check if user object is valid
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidTokenException());

        //Check if friend object is valid
        User friend = userRepository.findById(friendRequest.getFriendId())
                .orElseThrow(() -> new InvalidUserIdException());

        //Checks if the friendship exists already, if so throws DuplicatedFriendException()
        if (friendRepository.findByUserIdAndFriendId(user.getId(), friend.getId()).isPresent()) {
            throw new DuplicatedFriendException(friend.getUsername());
        }

        return createFriendship(user, friend);
    }

    public Response addFriendByUsername(String friendUsername, String username) {
        // check if friend username is valid
        User friend = userRepository.findByUsername(friendUsername).orElseThrow(() -> new InvalidUsernameException(friendUsername));

        // check if the user object is valid
        User user = userRepository.findByUsername(username).orElseThrow(() -> new InvalidUsernameException(username));

        //Checks if the friendship exists already, if so throws DuplicatedFriendException()
        if (friendRepository.findByUserIdAndFriendId(user.getId(), friend.getId()).isPresent()) {
            throw new DuplicatedFriendException(friend.getUsername());
        }

        return createFriendship(user, friend);
    }

    public Response createFriendship(User user, User friend) {

        //Create friend object from the friend userId
        Friend friendship1 = buildFriendObject(user, friend);

        //Create friend object from the friend userId
        Friend friendship2 = buildFriendObject(friend, user);

        //Saves ticket into database
        friendRepository.save(friendship1);
        friendRepository.save(friendship2);

        OutputFriend outputFriend = OutputFriend.builder()
                .friend(friend)
                .isApproved(false)
                .build();

        //If Everything goes smoothly, SuccessResponse will be created
        return SingleFriendResponse.builder()
                .friend(outputFriend)
                .build();

    }

    private Friend buildFriendObject(User user, User friend) {
        return Friend.builder()
                .userId(user.getId())
                .friendId(friend.getId())
                .isApproved(false)
                .build();
    }

    public Response deleteFriend(FriendRequest friendRequest, String username)
            throws InvalidTokenException, InvalidUserIdException{

        //Check if user object is valid
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidTokenException());

        //Check if friend object is valid
        User friend = userRepository.findById(friendRequest.getFriendId())
                .orElseThrow(() -> new InvalidUserIdException());

        Friend friendship = friendRepository.findByUserIdAndFriendId(user.getId(), friend.getId())
                .orElseThrow(() -> new FriendNotFoundException(user.getId(), friend.getId()));

        //Delete friend from friendship
        friendRepository.deleteByUserIdAndFriendId(user.getId(), friend.getId());
        friendRepository.deleteByUserIdAndFriendId(friend.getId(), user.getId());

        OutputFriend outputFriend = OutputFriend.builder()
                .friend(friend)
                .isApproved(friendship.isApproved())
                .build();

        //If Everything goes smoothly, SuccessResponse will be created
        return SingleFriendResponse.builder()
                .friend(outputFriend)
                .build();
    }

    public Response approveFriend(FriendRequest friendRequest, String username) {
        //Check if user object is valid
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidTokenException());

        //Check if friend object is valid
        User friend = userRepository.findById(friendRequest.getFriendId())
                .orElseThrow(() -> new InvalidUserIdException());

        //Find both friends
        Friend friendship1 = friendRepository.findByUserIdAndFriendId(user.getId(), friend.getId())
                .orElseThrow(() -> new FriendNotFoundException(user.getUsername(), friend.getUsername()));
        Friend friendship2 = friendRepository.findByUserIdAndFriendId(friend.getId(), user.getId())
                .orElseThrow(() -> new FriendNotFoundException(friend.getUsername(), user.getUsername()));

        //Approve both friends
        friendship1.setApproved(true);
        friendship2.setApproved(true);

        //Update friendship back to repository
        friendRepository.save(friendship1);
        friendRepository.save(friendship2);

        OutputFriend outputFriend = OutputFriend.builder()
                .friend(friend)
                .isApproved(true)
                .build();

        //If Everything goes smoothly, SuccessResponse will be created
        return SingleFriendResponse.builder()
                .friend(outputFriend)
                .build();
    }

    public Response getAllFriends(String username) {

        //Check if user object is valid
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidTokenException());

        //Obtain friendList from username
        List<Friend> friendList = friendRepository.findAllByUserId(user.getId());

        //If Everything goes smoothly, SuccessResponse will be created
        return FriendResponse.builder()
                .friends(convertFriendListToOutputFriendList(friendList))
                .build();
    }

    public Response getApprovedFriends(String username) {
        //Check if user object is valid
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidTokenException());

        //Obtain friendList from username
        List<Friend> friendList = friendRepository.findAllByUserId(user.getId());
        filterFriendList(friendList, true);

        //If Everything goes smoothly, SuccessResponse will be created
        return FriendResponse.builder()
                .friends(convertFriendListToOutputFriendList(friendList))
                .build();
    }

    public Response getPendingFriends(String username) {

        //Check if user object is valid
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidTokenException());

        //Obtain friendList from username
        List<Friend> friendList = friendRepository.findAllByUserId(user.getId());
        filterFriendList(friendList, false);

        //If Everything goes smoothly, SuccessResponse will be created
        return FriendResponse.builder()
                .friends(convertFriendListToOutputFriendList(friendList))
                .build();
    }

    private void filterFriendList(List<Friend> list, boolean isApproved) {
        Iterator<Friend> iter = list.iterator();
        while (iter.hasNext()) {
            Friend friend = iter.next();
            if (friend.isApproved() != isApproved) {
                iter.remove();
            }
        }
    }

    private List<OutputFriend> convertFriendListToOutputFriendList(List<Friend> list) {
        List<OutputFriend> out = new ArrayList<>();
        for (Friend friend : list) {
            User friendUser = userRepository.findById(friend.getFriendId())
                    .orElseThrow(() -> new InvalidUserIdException());
            OutputFriend outputFriend = OutputFriend.builder()
                    .friend(friendUser)
                    .isApproved(friend.isApproved())
                    .build();
            out.add(outputFriend);
        }
        return out;
    }

}
