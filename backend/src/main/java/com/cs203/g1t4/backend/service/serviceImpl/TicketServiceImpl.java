package com.cs203.g1t4.backend.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cs203.g1t4.backend.data.request.ticket.TicketRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.ticket.SingleTicketResponse;
import com.cs203.g1t4.backend.data.response.ticket.TicketResponse;
import com.cs203.g1t4.backend.models.Ticket;
import com.cs203.g1t4.backend.models.User;
import com.cs203.g1t4.backend.models.event.Event;
import com.cs203.g1t4.backend.models.exceptions.DuplicateTicketException;
import com.cs203.g1t4.backend.models.exceptions.InvalidEventIdException;
import com.cs203.g1t4.backend.models.exceptions.InvalidTicketIdException;
import com.cs203.g1t4.backend.models.exceptions.InvalidTokenException;
import com.cs203.g1t4.backend.models.exceptions.NoTicketsAvailableException;
import com.cs203.g1t4.backend.repository.EventRepository;
import com.cs203.g1t4.backend.repository.TicketRepository;
import com.cs203.g1t4.backend.repository.UserRepository;
import com.cs203.g1t4.backend.service.services.TicketService;

import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    /**
     * Creates a Ticket from the ticketRequest and username and saves it into repository
     * If user cannot be found in the repository from token, throws InvalidTokenException.
     * If user has already purchased more than 4 tickets for the concert, throws DuplicateTicketException.
     *
     * @param ticketRequest a TicketRequest object containing the new Ticket information
     * @param username a String object containing the username of the token
     * @return a SingleTicketResponse object containing the ticket that was created
     */
    public SingleTicketResponse createTicket(TicketRequest ticketRequest, String username)
            throws InvalidTokenException, DuplicateTicketException{
        // Get the buying user's id
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidTokenException());

        // Check if user has purchased 4 tickets already
        List<Ticket> purchasedTickets = ticketRepository.findAllByEventIdAndUserIdBuyer(ticketRequest.getEventId(), user.getId());
        if (purchasedTickets.size() >= 4) {
            throw new DuplicateTicketException(ticketRequest.getEventId(), user.getId());
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

    /**
     * Deletes a Ticket from the repository based on the ticketId
     * If ticket cannot be found in the repository from token, throws InvalidTicketIdException.
     * If user cannot be found in the repository from token, throws InvalidTokenException.
     *
     * @param ticketId a String object containing the ticketId of the ticket to be deleted
     * @return a SingleTicketResponse object containing the ticket that was deleted
     */
    public Response deleteTicket(String ticketId) throws InvalidTicketIdException{

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new InvalidTicketIdException(ticketId));

        ticketRepository.deleteById(ticketId);

        return SingleTicketResponse.builder()
                .ticket(ticket)
                .build();
    }


    /**
     * Updates a Ticket from the repository based on the ticketId using the TicketRequest
     * If user cannot be found in the repository from token, throws InvalidTokenException.
     * If ticket cannot be found in the repository from token, throws InvalidTicketIdException.
     *
     * @param ticketId a String object containing the ticketId of the ticket to be updated.
     * @param ticketRequest a TicketRequest object containing the updated information of the ticket.
     * @param username a String object containing the username of the user.
     * @return a SingleTicketResponse object containing the ticket that was updated.
     */
    public Response updateTicket(String ticketId, TicketRequest ticketRequest, String username)
            throws InvalidTokenException, InvalidTicketIdException{
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

    /**
     * Get a Ticket from the repository based on the ticketId using the TicketRequest
     * If ticket cannot be found in the repository from token, throws InvalidTicketIdException.
     *
     * @param ticketId a String object containing the ticketId of the ticket to be found.
     * @return a SingleTicketResponse object containing the ticket that was updated.
     */
    public Response getTicketById(String ticketId) throws InvalidTicketIdException {

        // Obtain ticket from repository using its id
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new InvalidTicketIdException(ticketId));

        //If Everything goes smoothly, SuccessResponse will be created
        return SingleTicketResponse.builder()
                .ticket(ticket)
                .build();
    }

    /**
     * Get List of Tickets from the repository based on the user token
     * If user cannot be found in the repository from token, throws InvalidTokenException.
     *
     * @param username a String object containing the username of the token
     * @return a TicketResponse object containing the List of Tickets that was found.
     */
    public Response getTicketsByUser(String username) throws InvalidTokenException {
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

    /**
     * Get List of Tickets from the repository based on the eventId
     * If event cannot be found in the repository from eventId, throws InvalidEventIdException.
     *
     * @param eventId a String object containing the eventId of the event
     * @return a TicketResponse object containing the List of Tickets that was found.
     */
    public Response getTicketByEvent(String eventId) throws InvalidEventIdException {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new InvalidEventIdException(eventId));

        List<Ticket> list = ticketRepository.findByEventId(eventId);

        return TicketResponse.builder()
                .tickets(list)
                .build();

    }

    // helper methods

    // temporary method to create a seat value for the event
//    public String seatNoGenerator(String eventId) {
//        // first determine how many tickets are bought
//        List<Ticket> tickets = ticketRepository.findByEventId(eventId);
//
//        // fill 1 - 20 in each row first,
//        // then fill from a to j (total 10 rows) rows
//        // means each event seats 200 people
//
//        // first find the number of seats filled
//        int seatsFilled = tickets.size();
//        System.out.println(seatsFilled);
//
//        // check if this event still can buy tickets
//        if (seatsFilled == 200) {
//            throw new NoTicketsAvailableException(eventId);
//        }
//
//        // check what is the current row
//        int rowSeatsFilled = seatsFilled / 20;
//
//        // get new seatConfiguration
//        int newSeatNum = seatsFilled % 20 + 1;
//        char newSeatRow = (char) (seatsFilled % 20 < newSeatNum ? 'A' + rowSeatsFilled : 'A' + rowSeatsFilled + 1);
//
//        return String.format("%02d%s", newSeatNum, newSeatRow);
//    }

}
