package com.cs203.g1t4.backend.service.serviceImpl;

import com.cs203.g1t4.backend.data.request.venue.VenueRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.data.response.venue.SingleVenueResponse;
import com.cs203.g1t4.backend.models.Venue;
import com.cs203.g1t4.backend.models.exceptions.InvalidArtistIdException;
import com.cs203.g1t4.backend.models.exceptions.InvalidVenueException;
import com.cs203.g1t4.backend.repository.VenueRepository;
import com.cs203.g1t4.backend.service.services.S3Service;
import com.cs203.g1t4.backend.service.services.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class VenueServiceImpl implements VenueService {
    private final VenueRepository venueRepository;
    private final S3Service s3Service;

    @Value("${aws.bucket.name}")
    private String bucketName;

    /**
     * Finds a Venue from the repository based on the venueId.
     * If venue cannot be found in the repository, throws InvalidVenueException.
     * 
     * @param venueId a String object containing the venueId to be found
     * @return a SingleVenueResponse object containing the found Venue object
     */
    public Response getVenue(String venueId) throws InvalidVenueException {
        // find the venue from the repository
        Venue venue = venueRepository.findById(venueId).orElseThrow(() -> new InvalidVenueException());

        // Get the venue image URL 
        String venueImageURL = "https://%s.s3.ap-southeast-1.amazonaws.com/venue-images/%s/%s"
                .formatted(bucketName, venueId, venue.getVenueImageName());

         //If Everything goes smoothly, SuccessResponse will be created
        return SingleVenueResponse.builder()
                .venue(venue)
                .imageURL(venueImageURL)
                .build();
    }

    /**
     * Creates a Venue object from a VenueRequest Object to be stored into repository
     *
     * @param venueRequest a VenueRequest object containing the information of the new venue to be created
     * @param image a MultipartFile object containing the image of the venue
     * @return SuccessResponse "Venue has been created successfully"
     */
    public Response createVenue(VenueRequest venueRequest, MultipartFile image) {

        // Get the venue image name
        String venueImageName = image.getOriginalFilename();

        Venue venue = Venue.builder()
                .address(venueRequest.getAddress())
                .locationName(venueRequest.getLocationName())
                .postalCode(venueRequest.getPostalCode())
                .description(venueRequest.getDescription())
                .venueImageName(venueImageName)
                .build();

        venueRepository.save(venue);

        // Put the image into the bucket
        s3Service.putObject(
            bucketName,
            "venue-images/%s/%s".formatted(venue.getId(), venueImageName),
            image
        );

        //If Everything goes smoothly, SuccessResponse will be created
        return SuccessResponse.builder()
                .response("Venue has been created successfully")
                .build();
    }

    /**
     * Updates a Venue with new venue information and image based on its VenueId.
     * If venue cannot be found in the repository, throws InvalidVenueException.
     *
     * @param venueId a String object containing the venueId of the venue to be updated
     * @param venueRequest a VenueRequest object containing the new venue info to be updated
     * @param image a MultipartFile object containing the new image to be updated
     * @return a SingleVenueResponse object containing the updated Venue object
     */
    public Response updateVenue(String venueId, VenueRequest venueRequest, MultipartFile image)
            throws InvalidVenueException {
        
        // Find the venue within the venue repository
        Venue venue = venueRepository.findById(venueId).orElseThrow(() -> new InvalidVenueException());

        // Get the venue image name
        String venueImageName = venue.getVenueImageName();

        // Get the venue image URL 
        String venueImageURL = "https://%s.s3.ap-southeast-1.amazonaws.com/venue-images/%s/%s"
                .formatted(bucketName, venueId, venueImageName);

        // Create the update venue
        Venue updatedVenue = Venue.builder()
                .id(venue.getId())
                .address(venueRequest.getAddress())
                .locationName(venueRequest.getLocationName())
                .postalCode(venueRequest.getPostalCode())
                .description(venueRequest.getDescription())
                .venueImageName(venueImageName)
                .build();

        // Save the new updatedVenue
        venueRepository.save(updatedVenue);

        if (image != null && !image.isEmpty()) {
            // Put the image into the bucket
            s3Service.putObject(
                    bucketName,
                    "venue-images/%s/%s".formatted(venueId, venueImageName),
                    image
            );
        }
        return SingleVenueResponse.builder()
                .venue(updatedVenue)
                .imageURL(venueImageURL)
                .build();
    }

    /**
     * Removes a Venue from the repository based in its venueId.
     * If venue cannot be found in the repository, throws InvalidVenueException.
     *
     * @param venueId a String object containing the venueId of the venue to be removed
     * @return a SingleVenueResponse object containing the venue that was removed
     */
    public Response removeVenue(String venueId) throws InvalidVenueException {
        Venue venue = venueRepository.findById(venueId).orElseThrow(() -> new InvalidVenueException());
        venueRepository.deleteById(venueId);

        return SingleVenueResponse.builder()
                .venue(venue)
                .build();
    }


}
