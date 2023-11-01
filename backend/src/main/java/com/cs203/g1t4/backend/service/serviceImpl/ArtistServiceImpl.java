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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtistServiceImpl implements ArtistService {
    private final ArtistRepository artistRepository;
    private final S3Service s3Service;

    @Value("${aws.bucket.name}")
    private String bucketName;

    // for manually adding a artist for event creation
    public Response addArtist(ArtistRequest request) {

        // create artist object
        Artist artist = Artist.builder()
                .name(request.getName())
                .description(request.getDescription())
                .website(request.getWebsite())
                .artistImage(request.getArtistImage())
                .build();

        // save into repo
        artistRepository.save(artist);

        // return
        return SuccessResponse.builder()
                .response("Artist has been created successfully")
                .build();
    }

    public Response deleteArtistById(String artistId) {

        // find the artist to delete
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new InvalidArtistIdException(artistId));

        // delete from repo
        artistRepository.deleteById(artistId);

        // return
        return SingleArtistResponse.builder()
                .artist(artist)
                .build();
    }


    public Response updateArtistById(String artistId, ArtistRequest request) {

        // update information about the artist
        Artist newArtist = Artist.builder()
                .id(artistId)
                .name(request.getName())
                .description(request.getDescription())
                .website(request.getWebsite())
                .build();

        // save the artist
        artistRepository.save(newArtist);

        // return
        return SingleArtistResponse.builder()
                .artist(newArtist)
                .build();
    }

    // for the purpose of updating artist when there is call to them again
    public String updateArtistByName(Artist newArtist) {

        Artist originalArtist = artistRepository.findByName(newArtist.getName())
                .orElseThrow(() -> new InvalidArtistIdException(newArtist.getName()));

        Artist updatedArtist = Artist.builder()
                .id(originalArtist.getId())
                .name(newArtist.getName())
                .description(newArtist.getDescription())
                .website(newArtist.getWebsite())
                .artistImageURL(newArtist.getArtistImageURL())
                .build();

        artistRepository.save(updatedArtist);

        return updatedArtist.getId();
    }

    public Response findArtistById(String artistId) {

        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new InvalidArtistIdException(artistId));

        artist.setArtistImageURL(getArtistImage(artistId));

        return SingleArtistResponse.builder()
                .artist(artist)
                .build();
    }

    public Response getAllArtist() {

        List<Artist> artistList = artistRepository.findAll();

        for (Artist artist : artistList) {
            artist.setArtistImageURL(getArtistImage(artist.getId()));
        }

        return ArtistResponse.builder()
                .artists(artistList)
                .build();
    }

    // for manual changing of picture
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
                .artistImageURL("artist-images/%s/%s".formatted(artistId, artistImageName))
                .description(artist.getDescription())
                .build();

        // Save to repository
        artistRepository.save(newArtist);

        // Return the success response if the image was saved successfully
        return SuccessResponse.builder()
                .response("Artist image has been uploaded successfully")
                .build();
    }

    // depending on whether you are finding the image by spotify or by s3
    public SuccessResponse getArtistImageResponse(String artistId) {

        String artistImageUrl = getArtistImage(artistId);

        // Return
        return SuccessResponse.builder()
                .response("Artist Image URL: " + artistImageUrl)
                .build();
    }

    public String getArtistImage(String artistId) {

        // Get information on which artist to edit from
        Artist artist = artistRepository.findById(artistId).orElseThrow(() -> new InvalidArtistIdException(artistId));

        String artistImageUrl = artist.getArtistImageURL();

        // check if you are getting via s3 or spotify link
        if ( artistImageUrl == null || artistImageUrl.length() == 0) {
            artistImageUrl = "https://%s.s3.ap-southeast-1.amazonaws.com/artist-images/%s/%s".formatted(bucketName, artistId, artist.getArtistImage());
        }

        // Return
        return artistImageUrl;
    }

    // helper method
    public String fanRecordsCreationAndUpdate(Artist artist) {
        // Get information on which artist to edit from
        Optional<Artist> currentArtist = artistRepository.findByName(artist.getName());

        // check if there is already the artist
        if (currentArtist.isPresent()) {
            String currentArtistId = updateArtistByName(artist);
            return currentArtistId;
        }

        // save the artist in the repo
        Artist newArtist = artistRepository.save(artist);
        // Get information on which artist to edit from
        return newArtist.getId();
    }
}
