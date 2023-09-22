package com.cs203.g1t4.backend.controller.spotify;


import com.cs203.g1t4.backend.config.spotify.SpotifyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

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

//    @GetMapping(value = "/get-user-code")
//    public String getSpotifyUserCode(@RequestParam("code") String userCode /*, HttpServletResponse response*/) throws IOException {
//        String code = userCode;
//        AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code)
//                .build();
//
//        try {
//            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();
//
//            // Set access and refresh token for further "spotifyApi" object usage
//            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
//            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
//
//            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
//        } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
//            System.out.println("Error: " + e.getMessage());
//        }
//        System.out.println(spotifyApi.getAccessToken());
////        response.sendRedirect("http://localhost:3000/top-artists");
//        return spotifyApi.getAccessToken();
//    }
}