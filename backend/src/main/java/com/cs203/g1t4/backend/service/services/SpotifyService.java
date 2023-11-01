package com.cs203.g1t4.backend.service;

import org.springframework.stereotype.Service;

import com.cs203.g1t4.backend.data.response.Response;
import se.michaelthelin.spotify.SpotifyApi;

@Service
public interface SpotifyService {

    public Response spotifyGetMyTopArtists(SpotifyApi spotifyApi, String username);

    public Response validateAccount(SpotifyApi spotifyApi, String username);
}
