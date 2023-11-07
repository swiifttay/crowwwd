package com.cs203.g1t4.backend.service.serviceImpl;

import com.cs203.g1t4.backend.data.request.artist.ArtistRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.artist.ArtistResponse;
import com.cs203.g1t4.backend.data.response.artist.SingleArtistResponse;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.models.Artist;
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

    /**
     * for manually adding a artist for event creation
     * @param request a ArtistRequest object that contains information on the new artist to be added
     * @return a SuccessResponse to be returned "Artist has been created successfully"
     */
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

    /**
     *
     * @param artistId a String object that contains the artist id of the artist to be deleted
     * @return a SingleArtistResponse that contains the artist that was deleted
     *      or throws InvalidArtistIdException if the artistId was invalid
     */
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

    /**
     *
     * @param artistId a String object that contains the artistId of the artist to be updated
     * @param request  a ArtistRequest object that contains information on the artist updated details
     * @return a SingleArtistResponse object that contains information on the updated artist
     */
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

    /**
     * for the purpose of updating artist in the change from manually created artist
     * to a spotify created artist
     * @param newArtist an Artist object that contains information of the artist updates, with the name
     *                  of the artist remaining the same
     * @return a String object containing information on the updated artist
     */
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

    /**
     *
     * @param artistId a String object containing the artistId of the artist to be found
     * @return a SingleArtistResponse that contains information on the artist found
     *          or throw InvalidArtistIdException if the artistId is invalid
     */
    public Response findArtistById(String artistId) {

        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new InvalidArtistIdException(artistId));

        artist.setArtistImageURL(getArtistImage(artistId));

        return SingleArtistResponse.builder()
                .artist(artist)
                .build();
    }

    /**
     * a method to get all the artists in the database
     * @return a ArtistResponse object that contains al the artists in the database
     */
    public Response getAllArtist() {

        List<Artist> artistList = artistRepository.findAll();

        for (Artist artist : artistList) {
            artist.setArtistImageURL(getArtistImage(artist.getId()));
        }

        return ArtistResponse.builder()
                .artists(artistList)
                .build();
    }

    /**
     * for updating the image for a manually created artist
     * @param artistId a String object that contains the artistId of the artist whos image is to be updating
     * @param multipartFile a MultipartFile object that contains the image to be uploaded to S3 bucket
     * @return a SuccessResponse object with information "Artist image has been uploaded successfully"
     *      if the image was uploaded successfully
     *      or throw InvalidArtistIdException if the artistId is invalid
     */
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

    /**
     * depending on whether you are finding the image by spotify or by s3
     * @param artistId a String object containing the artistId of the artist whos image is to be retrieved
     * @return SuccessResponse of the artist image url
     */
    public SuccessResponse getArtistImageResponse(String artistId) {

        String artistImageUrl = getArtistImage(artistId);

        // Return
        return SuccessResponse.builder()
                .response("Artist Image URL: " + artistImageUrl)
                .build();
    }

    /**
     * an object to determine if there is a need to retrieve an image url from s3 (for manually created artist)
     * or just to retrieve from the image url for a spotify updated artist
     * @param artistId a String object containing the artistId of the artist whos image is to be retrieved
     * @return a String object of the URL for the image
     */
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

    /**
     * a method to find for the artist in the database and return the id
     * for the purpose of fansRecord creation
     * @param artist an Artist object containing information extracted from spotify
     * @return the id of the artist found in the repository or if created
     */
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
