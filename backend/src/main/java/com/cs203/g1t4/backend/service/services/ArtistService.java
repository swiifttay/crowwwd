package com.cs203.g1t4.backend.service;

import com.cs203.g1t4.backend.data.request.artist.ArtistRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.models.Artist;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ArtistService {

    // for manually adding a artist for event creation
    public Response addArtist(ArtistRequest request);

    public Response deleteArtistById(String artistId);


    public Response updateArtistById(String artistId, ArtistRequest request);

    // for the purpose of updating artist when there is call to them again
    public String updateArtistByName(Artist newArtist);

    public Response findArtistById(String artistId);

    public Response getAllArtist();

    // for manual changing of picture
    public SuccessResponse uploadArtistImage(String artistId, MultipartFile multipartFile);

    // depending on whether you are finding the image by spotify or by s3
    public SuccessResponse getArtistImageResponse(String artistId);

    public String getArtistImage(String artistId);

    // helper method
    public String fanRecordsCreationAndUpdate(Artist artist);
}
