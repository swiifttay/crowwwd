package com.cs203.g1t4.backend.controller;


import com.cs203.g1t4.backend.models.exceptions.InvalidSpotifyAccountException;
import com.cs203.g1t4.backend.service.SpotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/api/spotify")
@RequiredArgsConstructor
public class SpotifyController {


    @Autowired
    private SpotifyApi spotifyAPI;


    private final SpotifyService spotifyService;


    @GetMapping("/login")
    @ResponseBody
    public String spotifyLogin() {


        AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyAPI.authorizationCodeUri()
                .scope("user-read-private, user-read-email, user-top-read")
                .show_dialog(true)
                .build();

        final URI uri = authorizationCodeUriRequest.execute();
        return uri.toString();
    }

    @GetMapping(value = "/get-user-code")
    public String getSpotifyUserCode(@RequestParam("code") String userCode) throws IOException {
        String code = userCode;


        AuthorizationCodeRequest authorizationCodeRequest = spotifyAPI.authorizationCode(code)
                .build();

        try {
            // authorizationCodeCredentials expires in 1hour
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

            // Set access and refresh token for further "spotifyApi" object usage
            spotifyAPI.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyAPI.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

        } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
            throw new InvalidSpotifyAccountException();
        }


        return spotifyAPI.getAccessToken();
    }


    @PostMapping("/updateMyAccountFavouriteArtists")
    public void overallFunction(@AuthenticationPrincipal UserDetails userDetails) {

        // TODO: change this to edit to update the fanRecords of the user
        // now you can call the API services from spotify
        // and do the relevant FanRecord related stuffs
        spotifyService.spotifyGetMyTopArtists(spotifyAPI, userDetails.getUsername());
    }
}