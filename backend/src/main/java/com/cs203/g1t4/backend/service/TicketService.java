package com.cs203.g1t4.backend.service;

import com.cs203.g1t4.backend.data.request.ticket.CreateTicketRequest;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.models.Ticket;
import com.cs203.g1t4.backend.models.User;
import com.cs203.g1t4.backend.models.exceptions.InvalidTokenException;
import com.cs203.g1t4.backend.repository.TicketRepository;
import com.cs203.g1t4.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public SuccessResponse createTicket(CreateTicketRequest createTicketRequest, String username) {
        // Get the buying user's id
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidTokenException());

       // Create the Ticket object
        Ticket ticket = new Ticket().builder()
                .userIdAttending(createTicketRequest.getUserIdAttending())
                .eventId(createTicketRequest.getEventId())
                .userIdBuyer(user.getId())
                .seatNo(createTicketRequest.getSeatNo())
                .isSurpriseTicket(createTicketRequest.isSurpriseTicket())
                .build();

        //Saves ticket into database
        ticketRepository.save(ticket);

        //If Everything goes smoothly, SuccessResponse will be created
        return SuccessResponse.builder()
                .response("Ticket has been created successfully")
                .build();
    }

}
