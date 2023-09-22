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

//
//        // To get the URL for the artistImage
//        String artistImageUrl = "https://%s.s3.ap-southeast-1.amazonaws.com/artist-images/%s/%s".formatted(bucketName,artistId, artist.getArtistImage());
//        artist.setArtistImageURL(artistImageUrl);


        return SingleArtistResponse.builder()
                .artist(artist)
                .build();
    }

    public Response getAllArtist() {

        List<Artist> artistList = artistRepository.findAll();

        for (Artist currentArtist : artistList) {
            // To get the URL for the artistImage
            String artistImageUrl = "https://%s.s3.ap-southeast-1.amazonaws.com/artist-images/%s/%s".formatted(bucketName, currentArtist.getId(), currentArtist.getArtistImage());
            currentArtist.setArtistImageURL(artistImageUrl);
        }
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

    public String getArtistImageURL (String artistId) {
        // Get information on which artist to edit from
        Artist artist = artistRepository.findById(artistId).orElseThrow(() -> new InvalidArtistIdException(artistId));


        // To get the URL for the artistImage
        String artistImageUrl = "https://%s.s3.ap-southeast-1.amazonaws.com/artist-images/%s/%s".formatted(bucketName, artist.getId(), artist.getArtistImage());

        return artistImageUrl;

    }

    public SuccessResponse getArtistImage(String artistId) {

        String artistImageURL = getArtistImageURL(artistId);

        // Return
        return SuccessResponse.builder()
                .response("Artist Image URL: " + artistImageURL)
                .build();
    }

    public String fanRecordsCreationAndUpdate(Artist artist) {
        // Get information on which artist to edit from
        Optional<Artist> currentArtist = artistRepository.findByName(artist.getName());

        // check if there is already the artist
        if (currentArtist.isPresent()) {
            return currentArtist.get().getId();
        }

        // save the artist in the repo
        artistRepository.save(artist);
        // Get information on which artist to edit from
        currentArtist = artistRepository.findByName(artist.getName());

        return currentArtist.get().getId();
    }
}
