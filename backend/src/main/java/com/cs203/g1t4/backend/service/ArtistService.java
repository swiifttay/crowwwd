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
public class ArtistService {
    private final ArtistRepository artistRepository;
    private final S3Service s3Service;

    @Value("${aws.bucket.name}")
    private String bucketName;

    // for manually adding a artist for event creation
    public Response addArtist(ArtistRequest request, MultipartFile image) {

        String artistImageName = image.getOriginalFilename();

        // create artist object
        Artist artist = Artist.builder()
                .name(request.getName())
                .description(request.getDescription())
                .website(request.getWebsite())
                .artistImage(artistImageName)
                .build();

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
        Artist newArtist = Artist.builder()
                .id(artistId)
                .name(request.getName())
                .description(request.getDescription())
                .website(request.getWebsite())
                .artistImage(artist.getArtistImage())
                .artistImageURL(artist.getArtistImageURL())
                .build();

        if (image != null && !image.isEmpty()) {
            // Put the image into the bucket
            s3Service.putObject(
                    bucketName,
                    "artist-images/%s/%s".formatted(artistId, artist.getArtistImage()),
                    image
            );
            // reset the artist image url in the event the previous set one was from spotify
            artist.setArtistImageURL("https://%s.s3.ap-southeast-1.amazonaws.com/artist-images/%s/%s".formatted(bucketName, artist.getId(), artist.getArtistImage()));
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
            String currentArtistId = updateArtistByName(artist);
            return currentArtistId;
        }

        // save the artist in the repo
        Artist newArtist = artistRepository.save(artist);
        // Get information on which artist to edit from
        return newArtist.getId();
    }

    // for the purpose of updating artist when there is the old one to be replaced by spotify's information
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

}
