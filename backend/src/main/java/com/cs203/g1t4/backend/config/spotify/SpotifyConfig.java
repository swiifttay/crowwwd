package com.cs203.g1t4.backend.config.spotify;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.SpotifyApi;

import java.net.URI;

@Configuration
public class SpotifyConfig {
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/api/spotify/get-user-code");

    // spotify client id and secret key
//    @Value("${spotify.client.id}")
    @Value("${env.SPOTIFY_CLIENT_ID}")
    private String spotifyClientId;


//    @Value("${spotify.client.secretkey}")
    @Value("${env.SPOTIFY_SECRET_KEY")
    private String spotifySecretKey;

    @Bean
    public SpotifyApi spotifyApiCreator() {

        // Create the client using the client builder
        SpotifyApi spotifyApi = SpotifyApi.builder()
                .setClientId(spotifyClientId)
                .setClientSecret(spotifySecretKey)
                .setRedirectUri(redirectUri)
                .build();

        // Return the client
        return spotifyApi;
    }
}
