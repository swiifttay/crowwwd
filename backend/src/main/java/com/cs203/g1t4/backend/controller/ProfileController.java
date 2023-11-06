package com.cs203.g1t4.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.cs203.g1t4.backend.data.request.user.UpdateProfileRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.service.services.CommonService;
import com.cs203.g1t4.backend.service.services.ProfileService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ProfileController {

    private final ProfileService profileService;
    private final CommonService commonService;

    @PutMapping("/updateProfile")
    public ResponseEntity<Response> updateProfile(@Valid @RequestBody UpdateProfileRequest request, @AuthenticationPrincipal UserDetails userDetails) {

        // Get the username from the userDetails of the authenticated user
        String username = userDetails.getUsername();

        //Update Profile using updateProfile method in profileService
        //Throws a InvalidTokenException if username cannot be found in repository
        Response response = profileService.updateProfile(request, username);

        //Else, return ok response
        return ResponseEntity.ok(response);
    }

    @GetMapping("/findProfile")
    public ResponseEntity<Response> findProfile(@AuthenticationPrincipal UserDetails userDetails) {

        // Get the username from the userDetails of the authenticated user
        String username = userDetails.getUsername();

        //Find Profile based on the username by findProfile method in profileService
        //Throws a InvalidTokenException if username cannot be found in repository
        Response response = profileService.findProfile(username);

        //If successful, the response is encapsulated with HTTP code of 200(ok) and contains the User object
        return ResponseEntity.ok(response);
    }

    @GetMapping("/searchProfile/{username}")
    public ResponseEntity<Response> searchProfile(@PathVariable String username) {
        // Find a profile based on the username provided
        // Throws a InvalidUsername if username cannot be found in repository
        Response response = profileService.searchProfile(username);

        return ResponseEntity.ok(response);
    }
}
