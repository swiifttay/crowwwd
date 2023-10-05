package com.cs203.g1t4.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cs203.g1t4.backend.data.request.venue.VenueRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.service.VenueService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/venue")
@RequiredArgsConstructor
public class VenueController {
  private final VenueService venueService;

  @PostMapping("/createVenue")
  public ResponseEntity<Response> createVenue(@Valid @RequestBody VenueRequest venueRequest) {

    // Provide information from venueRequest to create a venue
    // in venueService
    Response response = venueService.createVenue(venueRequest);

    // Else, return ok response
    return ResponseEntity.ok(response);
  }

  @GetMapping("/getVenue/{venueId}")
  public ResponseEntity<Response> getVenue(@PathVariable String venueId) {
    Response response = venueService.getVenue(venueId);

    return ResponseEntity.ok(response);
  }

  @PutMapping("/updateVenue/{venueId}")
  public ResponseEntity<Response> updateVenue(@PathVariable String venueId, @Valid @RequestBody VenueRequest venueReuest) {
    Response response = venueService.updateVenue(venueId, venueReuest);

    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/deleteVenue/{venueId}")
  public ResponseEntity<Response> deleteVenue(@PathVariable String venueId) {
    Response response = venueService.removeVenue(venueId);

    return ResponseEntity.ok(response);
  }
}
