package com.cs203.g1t4.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cs203.g1t4.backend.data.request.venue.VenueRequest;
import com.cs203.g1t4.backend.data.response.Response;

@Service
public interface VenueService {

    /**
     * Finds a Venue from the repository based on the venueId
     * 
     * @param venueId a String object containing the venueId to be found
     * @return a SingleVenueResponse object containing the found Venue object
     */
    public Response getVenue(String venueId);

    /**
     * 
     * @param venueRequest a VenueRequest object containing the information of the new venue to be created
     * @param image a MultipartFile object containing the image of the venue
     * @return SuccessResponse "Venue has been created successfully"
     */
    public Response createVenue(VenueRequest venueRequest, MultipartFile image) ;

    /**
     * 
     * @param venueId a String object containing the venueId of the venue to be updated
     * @param venueRequest a VenueRequest object containing the new venue info to be updated
     * @param image a MultipartFile object containing the new image to be updated
     * @return a SingleVenueResponse object containing the updated Venue object
     */
    public Response updateVenue(String venueId, VenueRequest venueRequest, MultipartFile image);

    /**
     * 
     * @param venueId a String object containing the venueId of the venue to be removed
     * @return a SingleVenueResponse object containing the venue that was removed
     */
    public Response removeVenue(String venueId);
}
