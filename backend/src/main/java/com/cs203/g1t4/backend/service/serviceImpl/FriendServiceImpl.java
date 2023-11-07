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

    /**
     * a method that adds friends according to the username
     * @param friendUsername a String object that contains information on the username of the friend to add
     * @param username  a String object that contains information on the username of the user requesting
     * @return a SingleFriendResponse object that contains information on who is the other user
     *          and pending approval request
     *          or else throws InvalidUsernameException if any of the usernames provided are invalid
     */
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

    /**
     *
     * @param user the user who is requesting for friend
     * @param friend a User object that is the friend
     * @return a SingleFriendResponse object that contains information on who is the other user
     *      and pending approval request
     */
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

    /**
     *
     * @param user the user who is requesting for friend
     * @param friend a User object that is the friend
     * @return a Friend object containing the userid and friend's userid and approval status
     */
    private Friend buildFriendObject(User user, User friend) {
        return Friend.builder()
                .userId(user.getId())
                .friendId(friend.getId())
                .isApproved(false)
                .build();
    }

    /**
     *
     * @param friendRequest a FriendRequest object containing the id of the friend
     * @param username a String object containing the username of the user trying to delete friend
     * @return a SingleFriendResponse if the user successfully deleted the friend association
     * @throws InvalidTokenException if the user is not logged in correctly
     * @throws InvalidUserIdException if the friend id provided is incorrect
     */
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

    /**
     *
     * @param friendRequest a FriendRequest object containing the id of the friend
     * @param username a String object containing the username of the user trying to approve friend
     * @return a SingleFriendResponse showing the friend that has been added and the status
     * @throws InvalidTokenException if the user is not logged in correctly
     * @throws InvalidUserIdException if the friend id provided is incorrect
     */
    public Response approveFriend(FriendRequest friendRequest, String username)
            throws InvalidTokenException, InvalidUserIdException {
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

    /**
     *
     * @param username a String object containing the username of the user to be searched
     * @return a FriendResponse containing all the friends of the user
     *      otherwise throws InvalidTokenException if username provided is invalid
     */
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

    /**
     *
     * @param username a String object containing the username of the user to be searched
     * @return a FriendResponse containing all the approved friends of the user
     */
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

    /**
     *
     * @param username a String object containing the username of the user to be searched
     * @return a FriendResponse containing all the pending approval friends of the user
     */
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

    /**
     *
     * @param list a List containing all the friends
     * @param isApproved a boolean containing information if this filter is supposed to
     *                   find the friends that are approved or not
     */
    private void filterFriendList(List<Friend> list, boolean isApproved) {
        Iterator<Friend> iter = list.iterator();
        while (iter.hasNext()) {
            Friend friend = iter.next();
            if (friend.isApproved() != isApproved) {
                iter.remove();
            }
        }
    }

    /**
     *
     * @param list a List containing the Friend objects to be converted to an OutputFriend object
     * @return a List containing the OutputFriend objects converted
     */
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
