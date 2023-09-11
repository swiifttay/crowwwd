package com.cs203.g1t4.backend.controller;

import com.cs203.g1t4.backend.data.request.user.UpdateProfileRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.service.CommonService;
import com.cs203.g1t4.backend.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final CommonService commonService;

    @PutMapping("/updateProfile")
    public ResponseEntity<Response> updateProfile(@RequestBody UpdateProfileRequest request, @RequestHeader("Authorization") String token) {

        //Obtain username stored in token using returnOldUsername method in commonService
        String username = commonService.returnOldUsername(token);

        //Update Profile using updateProfile method in profileService
        //Throws a InvalidTokenException if username cannot be found in repository
        Response response = profileService.updateProfile(request, username);

        //Else, return ok response
        return ResponseEntity.ok(response);
    }

    @GetMapping("/findProfile")
    public ResponseEntity<Response> findProfile(@RequestHeader("Authorization") String token) {

        //Obtain username stored in token using returnOldUsername method in commonService
        String username = commonService.returnOldUsername(token);

        //Find Profile based on the username by findProfile method in profileService
        //Throws a InvalidTokenException if username cannot be found in repository
        Response response = profileService.findProfile(username);

        //If successful, the response is encapsulated with HTTP code of 200(ok) and contains the User object
        return ResponseEntity.ok(response);
    }
}
