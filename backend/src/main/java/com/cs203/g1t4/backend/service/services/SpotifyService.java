package com.cs203.g1t4.backend.service.services;

import org.springframework.stereotype.Service;

import com.cs203.g1t4.backend.data.response.Response;
import se.michaelthelin.spotify.SpotifyApi;

@Service
public interface SpotifyService {

    Response spotifyGetMyTopArtists(SpotifyApi spotifyApi, String username);

    Response validateAccount(SpotifyApi spotifyApi, String username);
}
