package com.cs203.g1t4.backend.controller;

import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.service.HoldingAreaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/queue")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class QueueController {
    private final HoldingAreaService holdingAreaService;


    @GetMapping("/join/{eventId}")
    public ResponseEntity<Response> joinQueue(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String eventId) {

        Response success = holdingAreaService.enterHoldingArea(userDetails.getUsername(), eventId);

        return ResponseEntity.ok(success);
    }

    @GetMapping("/check/{eventId}")
    public ResponseEntity<Response> checkQueue(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String eventId) {
        Response isNextInQueue = holdingAreaService.getQueueStatus(userDetails.getUsername(), eventId);

        return ResponseEntity.ok(isNextInQueue);
    }

}
