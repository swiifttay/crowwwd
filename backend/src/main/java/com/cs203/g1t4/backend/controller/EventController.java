package com.cs203.g1t4.backend.controller;

import com.cs203.g1t4.backend.data.request.event.EventRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.service.EventService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping(value = "/fullEvent", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Response> addEvent(@Valid @ModelAttribute EventRequest request, @RequestPart MultipartFile image) {
        // Add Events using addFullEvent method in eventService
        Response response = eventService.addFullEvent(request, image);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/fullEvent/{eventId}")
    public ResponseEntity<Response> getFullEvent(@PathVariable String eventId) {
        // Get event using getFulLEventById in eventService
        Response response = eventService.getFullEventById(eventId);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/fullEvent/{eventId}")
    public ResponseEntity<Response> deleteEvent(@PathVariable String eventId) {
        // Delete event using deleteFullEventById in eventService
        Response response = eventService.deleteFullEventById(eventId);

        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/fullEvent/{eventId}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Response> updateEvent(@PathVariable String eventId, @Valid @ModelAttribute EventRequest request, @RequestPart(required = false) MultipartFile image) {
        // Update event using updateFullEventById() in eventService
        Response response = eventService.updateFullEventById(eventId, request, image);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/exploreEvent/all")
    public ResponseEntity<Response> getAllExploreEventAfterToday() {
        // Get all events for the explore page
        Response response = eventService.getAllExploreEvents();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/detailsEvent/{eventId}")
    public ResponseEntity<Response> getDetailsEvent(@PathVariable String eventId) {
        // Get event for details page
        Response response = eventService.getDetailsEventById(eventId);

        return ResponseEntity.ok(response);
    }
}
