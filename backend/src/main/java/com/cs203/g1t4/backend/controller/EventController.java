package com.cs203.g1t4.backend.controller;

import com.cs203.g1t4.backend.data.request.event.EventRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.service.EventService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping("/addEvent")
    public ResponseEntity<Response> addEvent(@RequestBody EventRequest request) {

        // Add Events using addEvent method in profileService
        Response response = eventService.addEvent(request);

        // Else, return ok response
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteEvent/{eventId}")
    public ResponseEntity<Response> deleteEvent(@PathVariable String eventId) {

        // Add Events using addEvent method in profileService
        Response response = eventService.deleteEventById(eventId);

        // Else, return ok response
        return ResponseEntity.ok(response);
    }

    @PutMapping("/updateEvent/{eventId}")
    public ResponseEntity<Response> updateEvent(@PathVariable String eventId, @RequestBody EventRequest request) {

        // Add Events using addEvent method in profileService
        Response response = eventService.updateEventById(eventId, request);

        // Else, return ok response
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getEvent/{eventId}")
    public ResponseEntity<Response> getEventById(@PathVariable String eventId) {

        // Update Profile using updateProfile method in profileService
        // Throws a InvalidTokenException if username cannot be found in repository
        Response response = eventService.findEventById(eventId);

        // Else, return ok response
        return ResponseEntity.ok(response);
    }

}
