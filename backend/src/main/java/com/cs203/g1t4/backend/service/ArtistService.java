package com.cs203.g1t4.backend.service;

import com.cs203.g1t4.backend.data.request.artist.ArtistRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.artist.ArtistResponse;
import com.cs203.g1t4.backend.data.response.artist.SingleArtistResponse;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.models.Artist;
import com.cs203.g1t4.backend.models.exceptions.InvalidArtistIdException;
import com.cs203.g1t4.backend.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtistService {
    private final ArtistRepository artistRepository;
    private final S3Service s3Service;

    @Value("${env.AWS_BUCKET_NAME}")
    private String bucketName;

    public Response addArtist(ArtistRequest request) {


        Artist artist = Artist.builder()
                .name(request.getName())
                .description(request.getDescription())
                .website(request.getWebsite())
                .artistImage(request.getArtistImage())
                .build();

        artistRepository.save(artist);

        return SuccessResponse.builder()
                .response("Artist has been created successfully")
                .build();
    }

    public Response deleteArtistById(String artistId) {

        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new InvalidArtistIdException(artistId));

        artistRepository.deleteById(artistId);

        return SingleArtistResponse.builder()
                .artist(artist)
                .build();
    }

    public Response updateArtistById(String artistId, ArtistRequest request) {

        Artist oldArtist = artistRepository.findById(artistId)
                .orElseThrow(() -> new InvalidArtistIdException(artistId));

        Artist newArtist = Artist.builder()
                .id(oldArtist.getId())
                .name(request.getName())
                .description(request.getDescription())
                .website(request.getWebsite())
                .artistImage(request.getArtistImage())
                .build();

        artistRepository.save(newArtist);

        return SingleArtistResponse.builder()
                .artist(newArtist)
                .build();
    }

    public Response findArtistById(String artistId) {

        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new InvalidArtistIdException(artistId));

        return SingleArtistResponse.builder()
                .artist(artist)
                .build();
    }

    public Response getAllArtist() {

        List<Artist> artistList = artistRepository.findAll();

        return ArtistResponse.builder()
                .artists(artistList)
                .build();
    }
    public SuccessResponse uploadArtistImage(String artistId,
                                            MultipartFile multipartFile) {
        // Get information on which artist to edit from
        Artist artist = artistRepository.findById(artistId).orElseThrow(() -> new InvalidArtistIdException(artistId));

        // Get the artist image name
        String artistImageName = artist.getArtistImage();

        // Check if this is to update image of the artist or to input a new one
        if (artistImageName == null) {
            artistImageName = multipartFile.getOriginalFilename();
        }

        // Put the image into the bucket
        s3Service.putObject(
                bucketName,
                "artist-images/%s/%s".formatted(artistId, artistImageName),
                multipartFile
        );

        //Update information on the Artist Image Name if it changed previously
        Artist newArtist = Artist.builder()
                .id(artist.getId())
                .name(artist.getName())
                .artistImage(artistImageName)
                .description(artist.getDescription())
                .build();

        // Save to repository
        artistRepository.save(newArtist);

        // Return the success response if the image was saved successfully
        return SuccessResponse.builder()
                .response("Artist image has been uploaded successfully")
                .build();
    }




    public SuccessResponse getArtistImage(String artistId) {
        // Get information on which artist to edit from
        Artist artist = artistRepository.findById(artistId).orElseThrow(() -> new InvalidArtistIdException(artistId));

        String artistImageName = artist.getArtistImage();

        // Get the Artist Image URL
        String artistImageURL = s3Service.getObjectURL(bucketName,
                "artist-images/%s/%s".formatted(artistId, artistImageName));

        // Implement catch error in the event no image is saved.

        // Return
        return SuccessResponse.builder()
                .response("Artist Image URL: " + artistImageURL)
                .build();
    }

}
