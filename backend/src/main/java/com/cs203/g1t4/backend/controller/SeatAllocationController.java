package com.cs203.g1t4.backend.controller;

import com.cs203.g1t4.backend.data.request.event.SeatingDetailsRequest;
import com.cs203.g1t4.backend.data.request.seat.SeatRequest;
import com.cs203.g1t4.backend.data.request.seat.SeatsConfirmRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.service.SeatsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seatAllocation")
@RequiredArgsConstructor
public class SeatAllocationController {

    private final SeatsService seatsService;

    @GetMapping("{eventId}/{category}/{numSeats}")
    public ResponseEntity<Response> findSeats(@PathVariable("eventId") String eventId,
                                              @PathVariable("category") String category,
                                              @PathVariable("numSeats") String numSeats) {

        Response response = seatsService.findSeats(eventId, category, numSeats);

        return ResponseEntity.ok(response);
    }

    @PutMapping("{eventId}/{category}")
    public ResponseEntity<Response> confirmSeats(@PathVariable("eventId") String eventId,
                                                 @PathVariable("category") String category,
                                                 @AuthenticationPrincipal UserDetails userDetails,
                                                 @Valid @RequestBody SeatsConfirmRequest request) {

        // Get the username from the userDetails of the authenticated user
        String username = userDetails.getUsername();

        Response response = seatsService.confirmSeats(eventId, category, username, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{eventId}/{category}")
    public ResponseEntity<Response> cancelSeats(@PathVariable("eventId") String eventId,
                                                 @PathVariable("category") String category,
                                                 @Valid @RequestBody SeatRequest request) {

        Response response = seatsService.cancelSeats(eventId, category, request);

        return ResponseEntity.ok(response);
    }
}
