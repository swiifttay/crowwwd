package com.cs203.g1t4.backend.controller;

import com.cs203.g1t4.backend.data.request.event.EventRequest;
import com.cs203.g1t4.backend.data.request.ticket.TicketRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.service.ProfileService;
import com.cs203.g1t4.backend.service.TicketService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ticket")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class TicketController {

    private final TicketService ticketService;
    private final ProfileService profileService;

    @PostMapping("/createTicket")
    public ResponseEntity<Response> createTicket(@Valid @RequestBody TicketRequest TicketRequest, @AuthenticationPrincipal UserDetails userDetails) {

        // Get the username from the userDetails of the authenticated user
        String username = userDetails.getUsername();

        // Provide information from both CreateTicketRequest and userid to createTicket in TicketService
        //Throws a InvalidTokenException if username cannot be found in repository
        Response response = ticketService.createTicket(TicketRequest, username);

        //Else, return ok response
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteTicket/{ticketId}")
    public ResponseEntity<Response> deleteTicket(@PathVariable String ticketId) {

        Response response = ticketService.deleteTicket(ticketId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/updateTicket/{ticketId}")
    public ResponseEntity<Response> updateTicket(@PathVariable String ticketId, @Valid @RequestBody TicketRequest TicketRequest, @AuthenticationPrincipal UserDetails userDetails) {

        // Get the username from the userDetails of the authenticated user
        String username = userDetails.getUsername();

        // Provide information from both CreateTicketRequest and userid to createTicket in TicketService
        //Throws a InvalidTokenException if username cannot be found in repository
        Response response = ticketService.updateTicket(ticketId, TicketRequest, username);

        //Else, return ok response
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getTickets")
    public ResponseEntity<Response> getTicketsByUser(@AuthenticationPrincipal UserDetails userDetails) {

        // Get the username from the userDetails of the authenticated user
        String username = userDetails.getUsername();

        // Provide information from both CreateTicketRequest and userid to createTicket in TicketService
        //Throws a InvalidTokenException if username cannot be found in repository
        Response response = ticketService.getTicketsByUser(username);

        //Else, return ok response
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllTicketsByEvent/{eventId}")
    public ResponseEntity<Response> getTicketsByEvent(@PathVariable String eventId) {

        Response response = ticketService.getTicketByEvent(eventId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getTicket/{ticketId}")
    public ResponseEntity<Response> getTicketById(@PathVariable String ticketId) {
        Response response = ticketService.getTicketById(ticketId);

        return ResponseEntity.ok(response);
    }

}
