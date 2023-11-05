package com.cs203.g1t4.backend.service.services;

import com.cs203.g1t4.backend.data.request.artist.ArtistRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.models.Artist;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ArtistService {

    // for manually adding a artist for event creation
    Response addArtist(ArtistRequest request, MultipartFile image);

    // for the purpose of deleting an artist by id
    Response deleteArtistById(String artistId);

    // for the purpose of updating an artist manually 
    Response updateArtistById(String artistId, ArtistRequest request, MultipartFile image);

    // for the purpose of updating artist when there is call to them again
    String updateSpotifyArtistByName(Artist newArtist, String originalArtist);

    // for the purpose of getting the artist by their id
    Response getArtistById(String artistId);

    // for the purpose of getting all the artists in the database
    Response getAllArtist();

    // helper method
    String fanRecordsCreationAndUpdate(Artist artist);
}
