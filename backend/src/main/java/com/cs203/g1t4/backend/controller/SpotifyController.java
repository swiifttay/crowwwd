package com.cs203.g1t4.backend.controller;


import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.models.exceptions.InvalidSpotifyAccountException;
import com.cs203.g1t4.backend.service.services.SpotifyService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
@SecurityRequirement(name = "bearerAuth")
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
    public void getSpotifyUserCode(@RequestParam("code") String userCode, HttpServletResponse response) throws IOException {
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


        // return spotifyAPI.getAccessToken();
        response.sendRedirect("http://localhost:3000/userprofile");
    }

    @GetMapping(value ="/getSpotifyToken")
    public ResponseEntity<Response> validateMySpotifyAccount(@AuthenticationPrincipal UserDetails userDetails) {
        Response response = spotifyService.validateAccount(spotifyAPI, userDetails.getUsername());

        return ResponseEntity.ok(response);
    }


    @PostMapping("/updateMyAccountFavouriteArtists")
    public ResponseEntity<Response> overallFunction(@AuthenticationPrincipal UserDetails userDetails) {

        Response response = spotifyService.spotifyGetMyTopArtists(spotifyAPI, userDetails.getUsername());

        return ResponseEntity.ok(response);
    }
}