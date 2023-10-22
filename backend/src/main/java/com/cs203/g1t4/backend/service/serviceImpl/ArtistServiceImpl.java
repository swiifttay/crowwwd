package com.cs203.g1t4.backend.service.serviceImpl;

import com.cs203.g1t4.backend.data.request.artist.ArtistRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.artist.ArtistResponse;
import com.cs203.g1t4.backend.data.response.artist.SingleArtistResponse;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.models.Artist;
import com.cs203.g1t4.backend.models.exceptions.DuplicatedArtistException;
import com.cs203.g1t4.backend.models.exceptions.DuplicatedEventException;
import com.cs203.g1t4.backend.models.exceptions.InvalidArtistIdException;
import com.cs203.g1t4.backend.repository.ArtistRepository;
import com.cs203.g1t4.backend.service.services.ArtistService;
import com.cs203.g1t4.backend.service.services.S3Service;
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
    public Response addArtist(ArtistRequest request, MultipartFile image) {

        Artist artist = getArtistClassFromRequest(request, null);

        // set the image name
        String artistImageName = image.getOriginalFilename();
        artist.setArtistImage(artistImageName);

        // save into repo
        artistRepository.save(artist);

        // Put the image into the bucket
        s3Service.putObject(
                bucketName,
                "artist-images/%s/%s".formatted(artist.getId(), artistImageName),
                image
        );


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


    public Response updateArtistById(String artistId, ArtistRequest request, MultipartFile image) {

        // find the old artist
        Artist artist = artistRepository.findById(artistId).orElseThrow(() -> new InvalidArtistIdException(artistId));

        // update information about the artist
        Artist newArtist = getArtistClassFromRequest(request, artist);

        if (image != null && !image.isEmpty()) {
            // in the event the image is reset from a spotify one
            // need to reset the imagename
            if (newArtist.getArtistImage() == null) {
                newArtist.setArtistImage(image.getOriginalFilename());
            }

            // Put the image into the bucket
            s3Service.putObject(
                    bucketName,
                    "artist-images/%s/%s".formatted(artistId, newArtist.getArtistImage()),
                    image
            );
        }

        // save the artist
        artistRepository.save(newArtist);

        // return
        return SingleArtistResponse.builder()
                .artist(newArtist)
                .build();
    }

    public Response getArtistById(String artistId) {

        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new InvalidArtistIdException(artistId));

        if (artist.getArtistImage() != null) {
            artist.setArtistImage("https://%s.s3.ap-southeast-1.amazonaws.com/artist-images/%s/%s".formatted(bucketName, artist.getId(), artist.getArtistImage()));
        }

        return SingleArtistResponse.builder()
                .artist(artist)
                .build();
    }

    public Response getAllArtist() {

        List<Artist> artistList = artistRepository.findAll();

        for (Artist artist : artistList) {
            if (artist.getArtistImage() != null) {
                artist.setArtistImageURL("https://%s.s3.ap-southeast-1.amazonaws.com/artist-images/%s/%s".formatted(bucketName, artist.getId(), artist.getArtistImage()));
            }
        }

        return ArtistResponse.builder()
                .artists(artistList)
                .build();
    }

    // helper method
    public String fanRecordsCreationAndUpdate(Artist artist) {
        // Get information on which artist to edit from
        Optional<Artist> currentArtist = artistRepository.findByName(artist.getName());

        // check if there is already the artist
        if (currentArtist.isPresent()) {
            String currentArtistId = updateSpotifyArtistByName(artist);
            return currentArtistId;
        }

        // save the artist in the repo
        Artist newArtist = artistRepository.save(artist);

        // Get information on which artist to edit from
        return newArtist.getId();
    }

    // for the purpose of updating artist when there is the old one to be replaced by spotify's information
    public String updateSpotifyArtistByName(Artist newArtist) {

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

    public void artistRequestChecker(ArtistRequest artistRequest, Artist oldArtist) {
        /*
         * Check 1: Checks the request if there are other artists that have the same name
         *
         * Considers 2 scenarios to check for DuplicatedEventName:
         * 1. If addArtist(), the oldArtist is null
         * 2. If updateArtistById(), the oldArtist will not be null and if there's a change in the eventName
         */
        if (oldArtist == null || !(oldArtist.getName().equals(oldArtist.getName()))) {

            //Checks Repository for the artistId and eventName
            Optional<Artist> artist = artistRepository.findByName(artistRequest.getName());
            if (artist.isPresent()) {

                //If present, throw new DuplicatedEventException.
                throw new DuplicatedArtistException(artistRequest.getName());
            }
        }
    }

    public Artist getArtistClassFromRequest(ArtistRequest artistRequest, Artist oldArtist) {
        artistRequestChecker(artistRequest, oldArtist);

        // Build the new artist
        Artist artist = Artist.builder()
                .name(artistRequest.getName())
                .description(artistRequest.getDescription())
                .website(artistRequest.getWebsite())
                .build();

        //If updateArtist (oldArtist != null), set artistId to oldArtist artistId.
        if (oldArtist != null) {
            artist.setId(oldArtist.getId());
            artist.setArtistImage(oldArtist.getArtistImage());
        }

        return artist;
    }

}
