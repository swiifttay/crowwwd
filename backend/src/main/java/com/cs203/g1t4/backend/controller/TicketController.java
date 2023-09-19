package com.cs203.g1t4.backend.controller;

import com.cs203.g1t4.backend.data.request.ticket.CreateTicketRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.service.ProfileService;
import com.cs203.g1t4.backend.service.TicketService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ticket")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class TicketController {

    private final TicketService ticketService;
    private final ProfileService profileService;

    @PostMapping("/createTicket")
    public ResponseEntity<Response> createTicket(@RequestBody CreateTicketRequest createTicketRequest, @AuthenticationPrincipal UserDetails userDetails) {

        // Get the username from the userDetails of the authenticated user
        String username = userDetails.getUsername();

        // Provide information from both CreateTicketRequest and userid to createTicket in TicketService
        //Throws a InvalidTokenException if username cannot be found in repository
        Response response = ticketService.createTicket(createTicketRequest, username);

        //Else, return ok response
        return ResponseEntity.ok(response);
    }


}
