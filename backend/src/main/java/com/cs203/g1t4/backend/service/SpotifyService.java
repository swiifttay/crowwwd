package com.cs203.g1t4.backend.service;

import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.models.exceptions.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpotifyService {

    private final ArtistService artistService;
    private final FanRecordService fanRecordService;

    public Response spotifyGetMyTopArtists(SpotifyApi spotifyApi, String username) {

        final GetUsersTopArtistsRequest getUsersTopArtistsRequest = spotifyApi.getUsersTopArtists()
                .time_range("medium_term")
                .limit(10)
                .offset(0)
                .build();

        try {


            final Paging<Artist> artistPaging = getUsersTopArtistsRequest.execute();
            Artist[] artistArray = artistPaging.getItems();


            // determine the top 10 artists
            int top10Artist = Math.min(artistArray.length, 10);

            // convert these artists to our artist format
            List<String> topArtistIdSelections = new ArrayList<>();

            // get the information of the top 10 artists
            for (int i = 0; i < top10Artist; i++) {
                // get the currentArtist in spotify format
                Artist currentArtist = artistArray[i];

                // get the convertedArtist in the implemented format
                com.cs203.g1t4.backend.models.Artist convertedArtist =
                        com.cs203.g1t4.backend.models.Artist.builder()
                                .name(currentArtist.getName())
                                .website(currentArtist.getHref())
                                .artistImageURL(currentArtist.getImages()[0].getUrl())
                                .build();

                // check if this artist is in the database and if not, add it
                // then return id
                String currentArtistId = artistService.fanRecordsCreationAndUpdate(convertedArtist);

                // add into the new list for creating fan records later
                topArtistIdSelections.add(currentArtistId);
            }

            // get the conversion to fanRecords
            fanRecordService.updateRecordsFromSpotify(topArtistIdSelections, username);

            //If Everything goes smoothly, response will be created using AuthenticationResponse with token
            return SuccessResponse.builder()
                    .response("Fan records have been updated successfully")
                    .build();

        } catch (Exception e) {
            throw new InvalidTokenException();
        }

    }
}
