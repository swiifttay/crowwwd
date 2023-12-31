package com.cs203.g1t4.backend.controller;

import com.cs203.g1t4.backend.data.request.seat.FindSeatRequest;
import com.cs203.g1t4.backend.data.request.seat.SeatCancelRequest;
import com.cs203.g1t4.backend.data.request.seat.SeatsConfirmRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.service.services.SeatsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class SeatAllocationController {

    private final SeatsService seatsService;

    @GetMapping("/seat")
    public ResponseEntity<Response> findSeats(@AuthenticationPrincipal UserDetails userDetails,
                                              @Valid @RequestBody FindSeatRequest findSeatRequest) {

        // Get the username from the userDetails of the authenticated user
        String username = userDetails.getUsername();

        Response response = seatsService.findSeats(username, findSeatRequest);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/seat")
    public ResponseEntity<Response> confirmSeats(@AuthenticationPrincipal UserDetails userDetails,
                                                 @Valid @RequestBody SeatsConfirmRequest seatsConfirmRequest) {

        // Get the username from the userDetails of the authenticated user
        String username = userDetails.getUsername();

        Response response = seatsService.confirmSeats(username, seatsConfirmRequest);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/seat")
    public ResponseEntity<Response> cancelSeats(@Valid @RequestBody SeatCancelRequest request) {

        Response response = seatsService.cancelSeats(request);

        return ResponseEntity.ok(response);
    }
}
