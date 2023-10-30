package com.cs203.g1t4.backend.service;

import com.cs203.g1t4.backend.data.request.friend.FriendRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.friend.FriendResponse;
import com.cs203.g1t4.backend.data.response.friend.SingleFriendResponse;
import com.cs203.g1t4.backend.models.Friend;
import com.cs203.g1t4.backend.models.Friendship;
import com.cs203.g1t4.backend.models.User;
import com.cs203.g1t4.backend.models.exceptions.*;
import com.cs203.g1t4.backend.repository.FriendRepository;
import com.cs203.g1t4.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    public Response addFriend(FriendRequest friendRequest, String username)
            throws InvalidTokenException, InvalidUserIdException{

        //Check if user object is valid
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidTokenException());

        //Check if friend object is valid
        User friendUser = userRepository.findById(friendRequest.getFriendId())
                .orElseThrow(() -> new InvalidUserIdException());

        //Create friend object from the friend userId
        Friend friend = Friend.builder()
                .friendId(friendUser.getId())
                .approved(false)
                .build();

        //Obtain Friendship from FriendRepository
        Friendship friendship = obtainFriendShipObjectFromRepository(user);

        //Saves new Friend into the Friendship
        saveFriendIntoFriendShip(friendship, friend);

        //Saves ticket into database
        friendRepository.save(friendship);

        //If Everything goes smoothly, SuccessResponse will be created
        return SingleFriendResponse.builder()
                .friend(friend)
                .build();
    }

    private Friendship obtainFriendShipObjectFromRepository(User user) {
        Optional<Friendship> friendship = friendRepository.findByUserId(user.getId());
        if (friendship.isPresent()) { return friendship.get(); }

        Friendship newFriendShip = Friendship.builder()
                .userId(user.getId())
                .friends(new ArrayList<>())
                .build();

        friendRepository.save(newFriendShip);
        return newFriendShip;
    }

    private void saveFriendIntoFriendShip(Friendship friendship, Friend friend) throws IllegalArgumentException {
        List<Friend> friends = friendship.getFriends();
        for (Friend f : friends) {
            if (f.getFriendId().equals(friend.getFriendId())) {
                throw new IllegalArgumentException("Friend already exists in the friend list");
            }
        }
        friends.add(friend);
    }

    public Response deleteFriend(FriendRequest friendRequest, String username) {

        //Check if user object is valid
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidTokenException());

        //Find if user has any existing friends
        Friendship friendship = friendRepository.findByUserId(user.getId())
                .orElseThrow(() -> new InvalidTokenException());

        //Delete friend from friendship
        Friend deletedFriend = deleteFriendFromFriendship(friendship, friendRequest.getFriendId());

        //Update friendship back to repository
        friendRepository.save(friendship);

        return SingleFriendResponse.builder()
                .friend(deletedFriend)
                .build();
    }

    private Friend deleteFriendFromFriendship(Friendship friendship, String friendId){
        int index = findFriendIndex(friendship, friendId);

        return friendship.getFriends().remove(index);
    }

    private int findFriendIndex(Friendship friendship, String friendId) throws FriendNotFoundException {
        List<Friend> friends = friendship.getFriends();
        for (int i = 0 ; i < friends.size() ; i++) {
            Friend friend = friends.get(i);
            if (friend.getFriendId().equals(friendId)) {
                return i;
            }
        }
        throw new FriendNotFoundException(friendId);
    }

    public Response approveFriend(FriendRequest friendRequest, String username) {
        // Get the buying user's id
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidTokenException());

        //Find if user has any existing friends
        Friendship friendship = friendRepository.findByUserId(user.getId())
                .orElseThrow(() -> new InvalidTokenException());

        //Delete friend from friendship
        Friend updatedFriend = approveFriendFromFriendship(friendship, friendRequest.getFriendId());

        //Update friendship back to repository
        friendRepository.save(friendship);

        //If Everything goes smoothly, SuccessResponse will be created
        return SingleFriendResponse.builder()
                .friend(updatedFriend)
                .build();
    }

    private Friend approveFriendFromFriendship(Friendship friendship, String friendId){
        int index = findFriendIndex(friendship, friendId);

        Friend friend = friendship.getFriends().get(index);
        friend.setApproved(true);

        return friend;
    }

    public Response getAllFriends(String username) {

        //Obtain friendList from username
        List<Friend> friendList = getFriendListFromFriendShip(username);

        //If Everything goes smoothly, SuccessResponse will be created
        return FriendResponse.builder()
                .friends(friendList)
                .build();
    }

    public Response getApprovedFriends(String username) {
        //Obtain friendList from username
        List<Friend> friendList = getFriendListFromFriendShip(username);

        //Clean output list
        List<Friend> outputList = cleanOutputList(friendList, true);

        //If Everything goes smoothly, SuccessResponse will be created
        return FriendResponse.builder()
                .friends(outputList)
                .build();
    }

    public Response getPendingFriends(String username) {

        //Obtain friendList from username
        List<Friend> friendList = getFriendListFromFriendShip(username);

        //Clean output list
        List<Friend> outputList = cleanOutputList(friendList, false);

        //If Everything goes smoothly, SuccessResponse will be created
        return FriendResponse.builder()
                .friends(outputList)
                .build();

    }

    private List<Friend> getFriendListFromFriendShip(String username) {
        // Get the buying user's id
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidTokenException());

        // Obtain ticket from repository using its id
        Friendship friendship = friendRepository.findByUserId(user.getId())
                .orElseThrow(() -> new InvalidTokenException());

        return friendship.getFriends();
    }

    private List<Friend> cleanOutputList(List<Friend> friendList, boolean approved) {
        List<Friend> out = new ArrayList<>();
        for (Friend friend : friendList) {
            if (friend.isApproved() == approved) {
                out.add(friend);
            }
        }
        return out;
    }

}
