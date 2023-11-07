package com.cs203.g1t4.backend.service.serviceImpl;

import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.models.exceptions.InvalidTokenException;
import com.cs203.g1t4.backend.service.services.ArtistService;
import com.cs203.g1t4.backend.service.services.FanRecordService;
import com.cs203.g1t4.backend.service.services.ProfileService;
import com.cs203.g1t4.backend.service.services.SpotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.User;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpotifyServiceImpl implements SpotifyService {

    private final ArtistService artistService;
    private final FanRecordService fanRecordService;
    private final ProfileService profileService;

    /**
     *  this method will query the spotify api to determine who are the top artists of the users and from
     *  there infer that the user is a fan of that artist. therefore, the fanRecord object will also be made for
     *  the user. should the artist object have a match to our database, we will update the artist database with
     *  the new information from spotify. otherwise, a new artist will be created in the database with details
     *  extracted from the spotify artist object
     * @param spotifyApi the spotifyApi as wired from the spotifyApi controller containing information on the
     *                   user login details and for querying the spotify api
     * @param username a String object containing information on the user requesting to update their fanrecords
     * @return a SuccessResponse containing "Fan records have been updated successfully" if the fan records
     *      were successfully created
     *      otherwise will throw InvalidTokenException if the user login is wrong
     */
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

    /**
     * this method will determine if the user logged in via spotify matches their first login to prevent
     * against users who borrow other peoples spotify account to take advantage of the priority service that fans
     * get. it will either update the user records if this is the first spotify login, or it will check if it
     * matches the user records in our database
     * @param spotifyApi the spotifyApi as wired from the spotifyApi controller containing information on the
     *                   user login details and for querying the spotify api
     * @param username a String object containing the username of the user validating the login
     * @return a SuccessResponse containing the spotify login accessToken
     *      otherwise if the validation is false, throws InvalidTokenException and resets the spotifyApi object
     */
    public Response validateAccount(SpotifyApi spotifyApi, String username) {

        // get the user account 
        final GetCurrentUsersProfileRequest getUserAccountRequest = spotifyApi.getCurrentUsersProfile().build();

        try {
            final User spotifyUser = getUserAccountRequest.execute();

            String spotifyUserId = spotifyUser.getId();

            // determine if the user account name is valid according to database
            // to prevent a user from logging in using someone else's spotify account
            if (!profileService.validate(spotifyUserId, username)) {
                throw new InvalidTokenException();
            }

            // if it was valid, return the accesstoken 
            return SuccessResponse.builder()
                        .response(spotifyApi.getAccessToken())
                        .build();
                        
        } catch (Exception e) {
            spotifyApi.setAccessToken(null);
            throw new InvalidTokenException();
        }

    }
}
