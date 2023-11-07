package com.cs203.g1t4.backend.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cs203.g1t4.backend.data.request.venue.VenueRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.service.services.VenueService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class VenueController {
  private final VenueService venueService;

  @PutMapping(value = "/venue", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
  public ResponseEntity<Response> createVenue(@Valid @ModelAttribute VenueRequest venueRequest, @RequestPart MultipartFile image) {
    // Add Venue using createVenue method in venueService
    Response response = venueService.createVenue(venueRequest, image);

    return ResponseEntity.ok(response);
  }

  @GetMapping("/venue/{venueId}")
  public ResponseEntity<Response> getVenue(@PathVariable String venueId) {
    // Get venue using getVenue in venueService
    Response response = venueService.getVenue(venueId);

    return ResponseEntity.ok(response);
  }

  @PutMapping(value = "/venue/{venueId}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
  public ResponseEntity<Response> updateVenue(@PathVariable String venueId,@Valid @ModelAttribute VenueRequest venueRequest, @RequestPart(required = false) MultipartFile image) {
    // Update venue using updateVenue in venueService
    Response response = venueService.updateVenue(venueId, venueRequest, image);

    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/venue/{venueId}")
  public ResponseEntity<Response> deleteVenue(@PathVariable String venueId) {
    // Delete venue using removeVenue in venueService
    Response response = venueService.removeVenue(venueId);

    return ResponseEntity.ok(response);
  }
}
