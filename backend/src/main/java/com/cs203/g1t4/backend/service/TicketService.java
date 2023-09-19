package com.cs203.g1t4.backend.service;

import com.cs203.g1t4.backend.data.request.ticket.TicketRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.data.response.ticket.TicketResponse;
import com.cs203.g1t4.backend.models.Ticket;
import com.cs203.g1t4.backend.models.User;
import com.cs203.g1t4.backend.models.exceptions.InvalidTicketIdException;
import com.cs203.g1t4.backend.models.exceptions.InvalidTokenException;
import com.cs203.g1t4.backend.repository.TicketRepository;
import com.cs203.g1t4.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public SuccessResponse createTicket(TicketRequest TicketRequest, String username) {
        // Get the buying user's id
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidTokenException());

       // Create the Ticket object
        Ticket ticket = Ticket.builder()
                        .userIdAttending(TicketRequest.getUserIdAttending())
                        .eventId(TicketRequest.getEventId())
                        .userIdBuyer(user.getId())
                        .seatNo(TicketRequest.getSeatNo())
                        .isSurpriseTicket(TicketRequest.isSurpriseTicket())
                        .build();

        //Saves ticket into database
        ticketRepository.save(ticket);

        //If Everything goes smoothly, SuccessResponse will be created
        return SuccessResponse.builder()
                .response("Ticket has been created successfully")
                .build();
    }

    public SuccessResponse updateTicket(String ticketId, TicketRequest TicketRequest, String username) {
        // Get the buying user's id
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidTokenException());

        // Get the ticket from repository
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new InvalidTicketIdException(ticketId));

        // Checks if userIdBuyer of ticket matches id of user account
        if (!(ticket.getUserIdBuyer().equals(user.getId()))) {
            throw new InvalidTokenException();
        }

        // Update the Ticket object only allowed in 2 fields of userIdAttending and isSurprise Ticket
        ticket.setUserIdAttending(TicketRequest.getUserIdAttending());
        ticket.setSurpriseTicket(TicketRequest.isSurpriseTicket());

        //Saves ticket into database
        ticketRepository.save(ticket);

        //If Everything goes smoothly, SuccessResponse will be created
        return SuccessResponse.builder()
                .response("Ticket has been updated successfully")
                .build();
    }

    public Response getTicketsByUser(String username) {
        // Get the buying user's id
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidTokenException());

        // Obtain the list from repository
        List<Ticket> list = ticketRepository.findAllByUserIdAttending(user.getId());

        //If Everything goes smoothly, SuccessResponse will be created
        return TicketResponse.builder()
                .tickets(list)
                .build();
    }



}
