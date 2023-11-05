package com.cs203.g1t4.backend.service.serviceImpl;

import com.cs203.g1t4.backend.data.request.ticket.TicketRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.ticket.SingleTicketResponse;
import com.cs203.g1t4.backend.data.response.ticket.TicketResponse;
import com.cs203.g1t4.backend.models.Ticket;
import com.cs203.g1t4.backend.models.User;
import com.cs203.g1t4.backend.models.event.Event;
import com.cs203.g1t4.backend.models.exceptions.*;
import com.cs203.g1t4.backend.repository.EventRepository;
import com.cs203.g1t4.backend.repository.TicketRepository;
import com.cs203.g1t4.backend.repository.UserRepository;
import com.cs203.g1t4.backend.service.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public SingleTicketResponse createTicket(TicketRequest ticketRequest, String username) {
        // Get the buying user's id
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidTokenException());

        // Check if the ticket already exists
        Optional<Ticket> duplicateTicket = ticketRepository.findByEventIdAndUserIdAttending(ticketRequest.getEventId(), ticketRequest.getUserIdAttending());

        if (duplicateTicket.isPresent()) {
            throw new DuplicateTicketException(ticketRequest.getEventId(), ticketRequest.getUserIdAttending());
        }

       // Create the Ticket object
        Ticket ticket = ticketRequest.returnTicketFromRequest(user);

        //Saves ticket into database
        ticketRepository.save(ticket);

        //If Everything goes smoothly, SuccessResponse will be created
        return SingleTicketResponse.builder()
                .ticket(ticket)
                .build();
    }

    public Response deleteTicket(String ticketId) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new InvalidTicketIdException(ticketId));

        ticketRepository.deleteById(ticketId);

        return SingleTicketResponse.builder()
                .ticket(ticket)
                .build();
    }

    public Response updateTicket(String ticketId, TicketRequest ticketRequest, String username) {
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
        ticket.setUserIdAttending(ticketRequest.getUserIdAttending());
        ticket.setSurpriseTicket(ticketRequest.isSurpriseTicket());

        //Saves ticket into database
        ticketRepository.save(ticket);

        //If Everything goes smoothly, SuccessResponse will be created
        return SingleTicketResponse.builder()
                .ticket(ticket)
                .build();
    }

    public Response getTicketById(String ticketId) {

        // Obtain ticket from repository using its id
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new InvalidTicketIdException(ticketId));

        //If Everything goes smoothly, SuccessResponse will be created
        return SingleTicketResponse.builder()
                .ticket(ticket)
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

    public Response getTicketByEvent(String eventId) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new InvalidEventIdException(eventId));

        List<Ticket> list = ticketRepository.findByEventId(eventId);

        return TicketResponse.builder()
                .tickets(list)
                .build();

    }

    // helper methods

    // temporary method to create a seat value for the event
    public String seatNoGenerator(String eventId) {
        // first determine how many tickets are bought
        List<Ticket> tickets = ticketRepository.findByEventId(eventId);

        // fill 1 - 20 in each row first,
        // then fill from a to j (total 10 rows) rows
        // means each event seats 200 people

        // first find the number of seats filled
        int seatsFilled = tickets.size();
        System.out.println(seatsFilled);

        // check if this event still can buy tickets
        if (seatsFilled == 200) {
            throw new NoTicketsAvailableException(eventId);
        }

        // check what is the current row
        int rowSeatsFilled = seatsFilled / 20;

        // get new seatConfiguration
        int newSeatNum = seatsFilled % 20 + 1;
        char newSeatRow = (char) (seatsFilled % 20 < newSeatNum ? 'A' + rowSeatsFilled : 'A' + rowSeatsFilled + 1);

        return String.format("%02d%s", newSeatNum, newSeatRow);
    }

}
