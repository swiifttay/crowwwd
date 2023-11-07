package com.cs203.g1t4.backend.controller;

import com.cs203.g1t4.backend.data.request.event.SeatingDetailsRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.service.services.SeatingDetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EventSeatingDetailsController {

    private final SeatingDetailsService seatingDetailsService;

    @PostMapping("/event-seating-details/{eventId}")
    public ResponseEntity<Response> addEventSeatingDetails(@PathVariable("eventId") String eventId,
                                                           @Valid @RequestBody SeatingDetailsRequest request) {

        Response response = seatingDetailsService.addSeatingDetails(eventId, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/event-seating-details/{eventId}")
    public ResponseEntity<Response> deleteEventSeatingDetails(@PathVariable("eventId") String eventId) {

        Response response = seatingDetailsService.deleteSeatingDetails(eventId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/event-seating-details/{eventId}")
    public ResponseEntity<Response> updateEventSeatingDetails(@PathVariable("eventId") String eventId,
                                                              @Valid @RequestBody SeatingDetailsRequest request) {

        Response response = seatingDetailsService.updateSeatingDetails(eventId, request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/event-seating-details/{eventId}")
    public ResponseEntity<Response> getEventSeatingDetails(@PathVariable("eventId") String eventId) {

        Response response = seatingDetailsService.getSeatingDetailsById(eventId);

        return ResponseEntity.ok(response);
    }
}
