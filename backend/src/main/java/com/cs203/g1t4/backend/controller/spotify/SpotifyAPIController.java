package com.cs203.g1t4.backend.controller.spotify;

import org.springframework.beans.factory.annotation.Value;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;


@RestController
@RequestMapping("/api")
public class SpotifyAPIController {
//    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/api/get-user-code/");
//
//    // spotify client id and secret key
//    @Value("${env.SPOTIFY_CLIENT_ID}")
//    private String spotifyClientId;
//
//    @Value("${env.SPOTIFY_SECRET_KEY}")
//    private String spotifySecretKey;
//    SpotifyApi spotifyApi = new SpotifyApi.Builder()
//            .setClientId(spotifyClientId)
//            .setClientSecret(spotifySecretKey)
//            .setRedirectUri(redirectUri)
//            .build();
//
//    @GetMapping(value = "user-top-artists")
//    public Artist[] getUserTopArtists() {
//
//        final GetUsersTopArtistsRequest getUsersTopArtistsRequest = spotifyApi.getUsersTopArtists()
//                .time_range("medium_term")
//                .limit(10)
//                .offset(5)
//                .build();
//
//        try {
//            final Paging<Artist> artistPaging = getUsersTopArtistsRequest.execute();
//
//            // return top artists as JSON
//            return artistPaging.getItems();
//        } catch (Exception e) {
//            System.out.println("Something went wrong!\n" + e.getMessage());
//        }
//        return new Artist[0];
//    }
//
//
}
