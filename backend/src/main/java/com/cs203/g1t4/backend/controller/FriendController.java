package com.cs203.g1t4.backend.controller;

import com.cs203.g1t4.backend.data.request.friend.FriendRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.service.FriendService;
import com.cs203.g1t4.backend.service.ProfileService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class FriendController {

    private final FriendService friendService;
    private final ProfileService profileService;

    @PostMapping("/friend")
    public ResponseEntity<Response> addFriend(@Valid @RequestBody FriendRequest friendRequest, @AuthenticationPrincipal UserDetails userDetails) {

        //Get the username from the userDetails of the authenticated user
        String username = userDetails.getUsername();

        //Provide information for FriendService to perform addFriend
        Response response = friendService.addFriend(friendRequest, username);

        //Else, return ok response
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/friend")
    public ResponseEntity<Response> deleteFriend(@Valid @RequestBody FriendRequest friendRequest, @AuthenticationPrincipal UserDetails userDetails) {

        //Get the username from the userDetails of the authenticated user
        String username = userDetails.getUsername();

        //Provide information for FriendService to perform deleteFriend
        Response response = friendService.deleteFriend(friendRequest, username);

        //Else, return ok response
        return ResponseEntity.ok(response);
    }

    @PutMapping("/friend")
    public ResponseEntity<Response> updateTicket(@Valid @RequestBody FriendRequest friendRequest, @AuthenticationPrincipal UserDetails userDetails) {

        //Get the username from the userDetails of the authenticated user
        String username = userDetails.getUsername();

        //Provide information for FriendService to perform deleteFriend
        Response response = friendService.approveFriend(friendRequest, username);

        //Else, return ok response
        return ResponseEntity.ok(response);
    }

    @GetMapping("/friend")
    public ResponseEntity<Response> getFriends(@AuthenticationPrincipal UserDetails userDetails) {

        //Get the username from the userDetails of the authenticated user
        String username = userDetails.getUsername();

        //Provide information for FriendService to perform deleteFriend
        Response response = friendService.getAllFriends(username);

        //Else, return ok response
        return ResponseEntity.ok(response);
    }

    @GetMapping("/approvedFriend")
    public ResponseEntity<Response> getApprovedFriends(@AuthenticationPrincipal UserDetails userDetails) {

        //Get the username from the userDetails of the authenticated user
        String username = userDetails.getUsername();

        //Provide information for FriendService to perform deleteFriend
        Response response = friendService.getApprovedFriends(username);

        //Else, return ok response
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pendingFriend")
    public ResponseEntity<Response> getPendingFriends(@AuthenticationPrincipal UserDetails userDetails) {

        //Get the username from the userDetails of the authenticated user
        String username = userDetails.getUsername();

        //Provide information for FriendService to perform deleteFriend
        Response response = friendService.getPendingFriends(username);

        //Else, return ok response
        return ResponseEntity.ok(response);
    }

}
