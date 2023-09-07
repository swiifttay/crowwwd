package com.cs203.g1t4.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.service.EventService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

  private final EventService eventService;

  @PutMapping("/getEvent/{eventId}")
  public ResponseEntity<Response> updateProfile(@PathVariable String eventId) {

    // Update Profile using updateProfile method in profileService
    // Throws a InvalidTokenException if username cannot be found in repository
    Response response = eventService.findEventById(eventId);

    // Else, return ok response
    return ResponseEntity.ok(response);
  }

}
