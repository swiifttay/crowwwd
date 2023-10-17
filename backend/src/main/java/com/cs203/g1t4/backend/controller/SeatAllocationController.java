package com.cs203.g1t4.backend.controller;

import com.cs203.g1t4.backend.data.request.event.SeatingDetailsRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.service.SeatsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seatAllocation")
@RequiredArgsConstructor
public class SeatAllocationController {

    private final SeatsService seatsService;

    @GetMapping("{eventId}/{category}/{numSeats}")
    public ResponseEntity<Response> findSeats(@PathVariable("eventId") String eventId,
                                              @PathVariable("category") String category,
                                              @PathVariable("numSeats") String numSeats,
                                              @Valid @RequestBody SeatingDetailsRequest request) {

        Response response = seatsService.findSeats(eventId, category, numSeats);

        return ResponseEntity.ok(response);
    }

    @PutMapping("{eventId}/{category}/{numSeats}")
    public ResponseEntity<Response> confirmSeats(@PathVariable("eventId") String eventId) {

//        Response response = seatsService.confirmSeats(eventId);

//        return ResponseEntity.ok(response);
        return null;
    }
}
