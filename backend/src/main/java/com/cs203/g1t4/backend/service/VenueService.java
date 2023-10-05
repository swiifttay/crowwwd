package com.cs203.g1t4.backend.service;

import org.springframework.stereotype.Service;

import com.cs203.g1t4.backend.data.request.venue.VenueRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.venue.SingleVenueResponse;
import com.cs203.g1t4.backend.models.Venue;
import com.cs203.g1t4.backend.models.exceptions.InvalidVenueException;
import com.cs203.g1t4.backend.repository.VenueRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VenueService {
  private final VenueRepository venueRepository;

  public Response getVenue(String venueId) {
    Venue venue = venueRepository.findById(venueId).orElseThrow(() -> new InvalidVenueException());

    return SingleVenueResponse.builder().venue(venue).build();
  }
  
  public Response createVenue(VenueRequest venueRequest) {
    Venue venue = Venue.builder()
                    .address(venueRequest.getAddress())
                    .locationName(venueRequest.getLocationName())
                    .postalCode(venueRequest.getPostalCode())
                  .build();
    venueRepository.save(venue);

    return SingleVenueResponse.builder().venue(venue).build();
  }
  public Response updateVenue(String venueId, VenueRequest venueRequest) {
    Venue venue = venueRepository.findById(venueId).orElseThrow(() -> new InvalidVenueException());

    venue.setAddress(venueRequest.getAddress());
    venue.setLocationName(venueRequest.getLocationName());
    venue.setPostalCode(venueRequest.getPostalCode());

    venueRepository.save(venue);

    return SingleVenueResponse.builder().venue(venue).build();
  }

  public Response removeVenue(String venueId) {
    Venue venue = venueRepository.findById(venueId).orElseThrow(() -> new InvalidVenueException());
    venueRepository.deleteById(venueId);

    return SingleVenueResponse.builder().venue(venue).build();
  }

  
}
