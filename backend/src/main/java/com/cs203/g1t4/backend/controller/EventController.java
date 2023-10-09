package com.cs203.g1t4.backend.controller;

import com.cs203.g1t4.backend.data.request.event.EventRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.service.EventService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping("/addEvent")
    public ResponseEntity<Response> addEvent(@Valid @RequestBody EventRequest request) {

        // Add Events using addEvent method in profileService
        Response response = eventService.addFullEvent(request);

        // Else, return ok response
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteEvent/{eventId}")
    public ResponseEntity<Response> deleteEvent(@PathVariable String eventId) {

        // Add Events using addEvent method in profileService
        Response response = eventService.deleteFullEventById(eventId);

        // Else, return ok response
        return ResponseEntity.ok(response);
    }

    @PutMapping("/updateEvent/{eventId}")
    public ResponseEntity<Response> updateEvent(@PathVariable String eventId, @Valid @RequestBody EventRequest request) {

        // Add Events using addEvent method in profileService
        Response response = eventService.updateFullEventById(eventId, request);

        // Else, return ok response
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getEvent/{eventId}")
    public ResponseEntity<Response> getEventById(@PathVariable String eventId) {

        // Update Profile using updateProfile method in profileService
        // Throws a InvalidTokenException if username cannot be found in repository
        Response response = eventService.findFullEventById(eventId);

        // Else, return ok response
        return ResponseEntity.ok(response);
    }
  
    @GetMapping("/getAllEvents")
        public ResponseEntity<Response> getAllEventsAfterToday() {

            // Update Profile using updateProfile method in profileService
            // Throws a InvalidTokenException if username cannot be found in repository
            Response response = eventService.findAllExploreEvents();

            // Else, return ok response
            return ResponseEntity.ok(response);
        }

    @GetMapping("/getEventsBetween/start/{start}/end/{end}")
    public ResponseEntity<Response> getEventsBetween(@PathVariable String start, @PathVariable String end) {
        // Find the Event that is between the start date and end date
        Response response = eventService.getEventBetweenDateRange(start, end);

        // Else, return ok response
        return ResponseEntity.ok(response);
    }

    @PostMapping("{eventId}/event-image")
    public ResponseEntity<Response> uploadEventImage(
            @PathVariable("eventId") String eventId,
            @RequestBody MultipartFile multipartFile) {

        // Upload the event image
        Response response = eventService.uploadEventImage(eventId, multipartFile);

        return ResponseEntity.ok(response);
    }

    @GetMapping("{eventId}/event-image")
    public ResponseEntity<Response> getEventImage(@PathVariable("eventId") String eventId) {

        // Upload the event image
        Response response = eventService.getEventImage(eventId);

        return ResponseEntity.ok(response);
    }

}
