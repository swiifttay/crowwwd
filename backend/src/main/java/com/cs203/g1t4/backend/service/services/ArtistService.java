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
    Response addArtist(ArtistRequest request);

    Response deleteArtistById(String artistId);


    Response updateArtistById(String artistId, ArtistRequest request);

    // for the purpose of updating artist when there is call to them again
    String updateArtistByName(Artist newArtist);

    Response findArtistById(String artistId);

    Response getAllArtist();

    // for manual changing of picture
    SuccessResponse uploadArtistImage(String artistId, MultipartFile multipartFile);

    // depending on whether you are finding the image by spotify or by s3
    SuccessResponse getArtistImageResponse(String artistId);

    String getArtistImage(String artistId);

    // helper method
    String fanRecordsCreationAndUpdate(Artist artist);
}
