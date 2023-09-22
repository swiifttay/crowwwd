package com.cs203.g1t4.backend.controller.spotify;


import com.cs203.g1t4.backend.config.spotify.SpotifyConfig;
import com.cs203.g1t4.backend.models.exceptions.InvalidSpotifyAccountException;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SpotifyConfigController {


    @Autowired
    private SpotifyConfig spotifyAPI;


    @GetMapping("/login")
    @ResponseBody
    public String spotifyLogin() {
        AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyAPI.spotifyApiCreator().authorizationCodeUri()
                .scope("user-read-private, user-read-email, user-top-read")
                .show_dialog(true)
                .build();

        final URI uri = authorizationCodeUriRequest.execute();
        return uri.toString();
    }

    @GetMapping(value = "/get-user-code")
    public String getSpotifyUserCode(@RequestParam("code") String userCode /*, HttpServletResponse response*/) throws IOException {
        String code = userCode;

        SpotifyApi spotifyApi = spotifyAPI.spotifyApiCreator();

        AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code)
                .build();

        try {
            // authorizationCodeCredentials expires in 1hour
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

            // Set access and refresh token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

        } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
            throw new InvalidSpotifyAccountException();
        }


//        response.sendRedirect("http://localhost:3000/top-artists");
        return spotifyApi.getAccessToken();
        // TODO: change this to edit to update the fanRecords of the user
        // now you can call the API services from spotify
        // and do the relevant FanRecord related stuffs
    }
}