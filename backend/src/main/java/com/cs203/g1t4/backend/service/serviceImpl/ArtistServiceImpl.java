package com.cs203.g1t4.backend.service.serviceImpl;

import com.cs203.g1t4.backend.data.request.artist.ArtistRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.artist.ArtistResponse;
import com.cs203.g1t4.backend.data.response.artist.SingleArtistResponse;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.models.Artist;
import com.cs203.g1t4.backend.models.exceptions.DuplicatedArtistException;
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
        Artist newArtist = getArtistClassFromRequest(request, artist);

        if (image != null && !image.isEmpty()) {
            // in the event the image is reset from a spotify one
            // need to reset the imagename
            if (newArtist.getArtistImage() == null) {
                newArtist.setArtistImage(image.getOriginalFilename());
                newArtist.setArtistImageURL(null);
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

        if (artist.getArtistImage() != null) {
            artist.setArtistImageURL("https://%s.s3.ap-southeast-1.amazonaws.com/artist-images/%s/%s".formatted(bucketName, artist.getId(), artist.getArtistImage()));
        }

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
            if (artist.getArtistImage() != null) {
                artist.setArtistImageURL("https://%s.s3.ap-southeast-1.amazonaws.com/artist-images/%s/%s".formatted(bucketName, artist.getId(), artist.getArtistImage()));
            }
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
        Optional<Artist> currentArtist = artistRepository.findByName(artist.getName());

        // check if there is already the artist
        if (currentArtist.isPresent()) {
            String currentArtistId = updateSpotifyArtistByName(artist, currentArtist.get().getId());
            return currentArtistId;
        }

        // save the artist in the repo
        Artist newArtist = artistRepository.save(artist);

        // Get information on which artist to edit from
        return newArtist.getId();
    }

    /**
     * depending on whether you are finding the image by spotify or by s3
     * @param artistId a String object containing the artistId of the artist whos image is to be retrieved
     * @return SuccessResponse of the artist image url
     */
    public SuccessResponse getArtistImageResponse(String artistId) {

        String artistImageUrl = getArtistImage(artistId);

        Artist updatedArtist = Artist.builder()
                .id(originalArtistId)
                .name(newArtist.getName())
                .description(newArtist.getDescription())
                .website(newArtist.getWebsite())
                .artistImageURL(newArtist.getArtistImageURL())
                .build();

    /**
     * an object to determine if there is a need to retrieve an image url from s3 (for manually created artist)
     * or just to retrieve from the image url for a spotify updated artist
     * @param artistId a String object containing the artistId of the artist whos image is to be retrieved
     * @return a String object of the URL for the image
     */
    public String getArtistImage(String artistId) {

        // Get information on which artist to edit from
        Artist artist = artistRepository.findById(artistId).orElseThrow(() -> new InvalidArtistIdException(artistId));
  
        return originalArtistId;
    }

    public void artistRequestChecker(ArtistRequest artistRequest, Artist oldArtist) {
        /*
         * Check 1: Checks the request if there are other artists that have the same name
         *
         * Considers 2 scenarios to check for DuplicatedEventName:
         * 1. If addArtist(), the oldArtist is null
         * 2. If updateArtistById(), the oldArtist will not be null and if there's a change in the eventName
         */
        if (oldArtist == null || !(oldArtist.getName().equals(artistRequest.getName()))) {

            //Checks Repository for the artistId and eventName
            Optional<Artist> artist = artistRepository.findByName(artistRequest.getName());
            if (artist.isPresent()) {

                //If present, throw new DuplicatedEventException.
                throw new DuplicatedArtistException(artistRequest.getName());
            }
        }
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
            artist.setArtistImageURL(oldArtist.getArtistImageURL());
        }

        return artist;
    }

}
