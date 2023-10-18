package com.cs203.g1t4.backend;

import com.cs203.g1t4.backend.data.request.ticket.TicketRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.data.response.ticket.SingleTicketResponse;
import com.cs203.g1t4.backend.data.response.ticket.TicketResponse;
import com.cs203.g1t4.backend.models.Ticket;
import com.cs203.g1t4.backend.models.User;
import com.cs203.g1t4.backend.models.event.Event;
import com.cs203.g1t4.backend.models.exceptions.DuplicateTicketException;
import com.cs203.g1t4.backend.models.exceptions.InvalidEventIdException;
import com.cs203.g1t4.backend.models.exceptions.InvalidTicketIdException;
import com.cs203.g1t4.backend.models.exceptions.InvalidTokenException;
import com.cs203.g1t4.backend.repository.EventRepository;
import com.cs203.g1t4.backend.repository.TicketRepository;
import com.cs203.g1t4.backend.repository.UserRepository;
import com.cs203.g1t4.backend.service.TicketService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Spy
    private TicketRepository ticketRepository;

    @Spy
    private UserRepository userRepository;

    @Spy
    private EventRepository eventRepository;

    @InjectMocks
    private TicketService ticketService;

    @Test
    void createTicket_UserFoundNoDuplicates_ReturnSuccessResponse() {

        // arrange
        User user = User.builder()
                .id("1234")
                .username("testUser")
                .build();

        TicketRequest ticketRequest = TicketRequest.builder()
                .eventId("1234")
                .userIdAttending("1234")
                .isSurpriseTicket(false)
                .build();

        Ticket ticket = Ticket.builder()
                .userIdAttending("1234")
                .eventId("1234")
                .userIdBuyer(user.getId())
                .seatNo("01A")
                .isSurpriseTicket(false)
                .build();

        // mock userRepository "findByUsername" operation
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user));

        // mock ticketRepository "findByEventIdAndUserIdAttending" operation
        when(ticketRepository.findByEventIdAndUserIdAttending(any(String.class), any(String.class))).thenReturn(Optional.empty());

        // mock "save" operation
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        // act
        SuccessResponse response = ticketService.createTicket(ticketRequest, user.getUsername());

        // assert
        assertNotNull(response);
        assertEquals("Ticket has been created successfully", response.getResponse());
        verify(userRepository).findByUsername(user.getUsername());
        verify(ticketRepository).findByEventIdAndUserIdAttending(ticket.getEventId(), user.getId());
        verify(ticketRepository).save(ticket);
    }

    @Test
    void createTicket_UserNotFound_InvalidTokenExceptionThrown() {

        // arrange
        User user = User.builder()
                .id("1234")
                .username("testUser")
                .build();

        TicketRequest ticketRequest = TicketRequest.builder()
                .eventId("1234")
                .userIdAttending("1234").build();

        // mock userRepository "findByUsername" operation
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

        InvalidTokenException e = assertThrows(InvalidTokenException.class, () -> {
            ticketService.createTicket(ticketRequest, user.getUsername());
        });

        // assert
        assertEquals("Token is not valid", e.getMessage());
        verify(userRepository).findByUsername(user.getUsername());
    }

    @Test
    void createTicket_UserFoundDuplicateTicket_DuplicateTicketExceptionThrown() {

        // arrange
        User user = User.builder()
                .id("1234")
                .username("testUser")
                .build();

        TicketRequest ticketRequest = TicketRequest.builder()
                .eventId("1234")
                .userIdAttending("1234").build();

        Ticket ticket = Ticket.builder()
                .id("1234")
                .eventId("1234")
                .userIdAttending("1234").build();

        // mock userRepository "findByUsername" operation
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user));

        // mock ticketRepository "findByEventIdAndUserIdAttending" operation
        when(ticketRepository.findByEventIdAndUserIdAttending(any(String.class), any(String.class))).thenReturn(Optional.of(ticket));

        DuplicateTicketException e = assertThrows(DuplicateTicketException.class, () -> {
            // act
            ticketService.createTicket(ticketRequest, user.getUsername());
        });

        // assert
        assertEquals("User 1234 already bought a ticket for 1234 event.", e.getMessage());
        verify(userRepository).findByUsername(user.getUsername());
        verify(ticketRepository).findByEventIdAndUserIdAttending(ticket.getEventId(), user.getId());
    }

    @Test
    void deleteTicket_TicketFound_ReturnDeletedTicketResponse() {

        // arrange
        Ticket ticket = Ticket.builder()
                .id("1234")
                .eventId("1234")
                .userIdAttending("1234").build();

        // mock ticketRepository "findById" operation
        when(ticketRepository.findById(any(String.class))).thenReturn(Optional.of(ticket));

        // mock ticketRepository "deleteById" operation
        doNothing().when(ticketRepository).deleteById(any(String.class));

        // act
        Response response = ticketService.deleteTicket(ticket.getId());

        // assert
        assertNotNull(response);
        verify(ticketRepository).findById(ticket.getId());
        verify(ticketRepository).deleteById(ticket.getId());
    }

    @Test
    void deleteTicket_TicketNotFound_InvalidTicketIdExceptionThrown() {

        // arrange
        Ticket ticket = Ticket.builder()
                .id("1234")
                .eventId("1234")
                .userIdAttending("1234").build();

        // mock ticketRepository "findById" operation
        when(ticketRepository.findById(any(String.class))).thenReturn(Optional.empty());

        InvalidTicketIdException e = assertThrows(InvalidTicketIdException.class, () -> {
            // act
            ticketService.deleteTicket(ticket.getId());
        });

        // assert
        assertEquals("Ticket with ticketId: 1234 does not exists", e.getMessage());
        verify(ticketRepository).findById(ticket.getId());
    }

    @Test
    void updateTicket_UserAndTicketFoundBuyerMatches_ReturnSuccessResponseWithUpdatedTicket() {

        // arrange
        Ticket oldTicket = Ticket.builder()
                .id("1234")
                .eventId("1234")
                .userIdAttending("1234")
                .userIdBuyer("1234")
                .isSurpriseTicket(false)
                .build();

        Ticket newTicket = Ticket.builder()
                .id("1234")
                .eventId("1234")
                .userIdAttending("5678")
                .userIdBuyer("1234")
                .isSurpriseTicket(true)
                .build();

        User user = User.builder()
                .id("1234")
                .username("testUser")
                .build();

        TicketRequest ticketRequest = TicketRequest.builder()
                .userIdAttending("5678")
                .isSurpriseTicket(true).build();

        // mock userRepository "findByUsername" operation
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user));

        // mock ticketRepository "findById" operation
        when(ticketRepository.findById(any(String.class))).thenReturn(Optional.of(oldTicket));

        // mock ticketRepository "save" operation
        when(ticketRepository.save(any(Ticket.class))).thenReturn(newTicket);

        // act
        SingleTicketResponse response = (SingleTicketResponse) ticketService.updateTicket(oldTicket.getId(), ticketRequest, user.getUsername());

        // assert
        assertNotNull(response);
        assertTrue(response.getTicket().isSurpriseTicket());
        assertEquals("5678", response.getTicket().getUserIdAttending());
        verify(userRepository).findByUsername(user.getUsername());
        verify(ticketRepository).findById(oldTicket.getId());
        verify(ticketRepository).save(newTicket);
    }

    @Test
    void updateTicket_UserNotFound_InvalidTokenExceptionThrown() {

        // arrange
        Ticket oldTicket = Ticket.builder()
                .id("1234")
                .eventId("1234")
                .userIdAttending("1234")
                .userIdBuyer("1234")
                .isSurpriseTicket(false)
                .build();

//        Ticket newTicket = Ticket.builder()
//                .id("1234")
//                .eventId("1234")
//                .userIdAttending("5678")
//                .userIdBuyer("1234")
//                .isSurpriseTicket(true)
//                .build();

        User user = User.builder()
                .id("1234")
                .username("testUser")
                .build();

        TicketRequest ticketRequest = TicketRequest.builder()
                .userIdAttending("5678")
                .isSurpriseTicket(true).build();

        // mock userRepository "findByUsername" operation
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

        InvalidTokenException e = assertThrows(InvalidTokenException.class, () -> {
            // act
            ticketService.updateTicket(oldTicket.getId(), ticketRequest, user.getUsername());
        });

        // assert
        assertEquals("Token is not valid", e.getMessage());
        verify(userRepository).findByUsername(user.getUsername());
    }

    @Test
    void updateTicket_UserFoundTicketNotFound_InvalidTicketIdExceptionThrown() {

        // arrange
        Ticket oldTicket = Ticket.builder()
                .id("1234")
                .eventId("1234")
                .userIdAttending("1234")
                .userIdBuyer("1234")
                .isSurpriseTicket(false)
                .build();

//        Ticket newTicket = Ticket.builder()
//                .id("1234")
//                .eventId("1234")
//                .userIdAttending("5678")
//                .userIdBuyer("1234")
//                .isSurpriseTicket(true)
//                .build();

        User user = User.builder()
                .id("1234")
                .username("testUser")
                .build();

        TicketRequest ticketRequest = TicketRequest.builder()
                .userIdAttending("5678")
                .isSurpriseTicket(true).build();

        // mock userRepository "findByUsername" operation
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user));

        // mock ticketRepository "findById" operation
        when(ticketRepository.findById(any(String.class))).thenReturn(Optional.empty());


        InvalidTicketIdException e = assertThrows(InvalidTicketIdException.class, () -> {
            // act
            ticketService.updateTicket(oldTicket.getId(), ticketRequest, user.getUsername());
        });

        // assert
        assertEquals("Ticket with ticketId: 1234 does not exists", e.getMessage());
        verify(userRepository).findByUsername(user.getUsername());
        verify(ticketRepository).findById(oldTicket.getId());
    }

    @Test
    void updateTicket_UserAndTicketFoundBuyerIdDoesNotMatch_InvalidTokenExceptionThrown() {

        // arrange
        Ticket oldTicket = Ticket.builder()
                .id("1234")
                .eventId("1234")
                .userIdAttending("1234")
                .userIdBuyer("1234")
                .isSurpriseTicket(false)
                .build();

//        Ticket newTicket = Ticket.builder()
//                .id("1234")
//                .eventId("1234")
//                .userIdAttending("5678")
//                .userIdBuyer("1234")
//                .isSurpriseTicket(true)
//                .build();

        User user = User.builder()
                .id("5678")
                .username("testUser")
                .build();

        TicketRequest ticketRequest = TicketRequest.builder()
                .userIdAttending("5678")
                .isSurpriseTicket(true).build();

        // mock userRepository "findByUsername" operation
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user));

        // mock ticketRepository "findById" operation
        when(ticketRepository.findById(any(String.class))).thenReturn(Optional.of(oldTicket));

        InvalidTokenException e = assertThrows(InvalidTokenException.class, () -> {
            // act
            ticketService.updateTicket(oldTicket.getId(), ticketRequest, user.getUsername());
        });

        // assert
        assertEquals("Token is not valid", e.getMessage());
        verify(userRepository).findByUsername(user.getUsername());
        verify(ticketRepository).findById(oldTicket.getId());
    }

    @Test
    void getTicketById_TicketFound_ReturnSingleTicketResponse() {

        // arrange
        Ticket ticket = Ticket.builder()
                .id("1234")
                .eventId("1234")
                .userIdAttending("1234")
                .userIdBuyer("1234")
                .isSurpriseTicket(false)
                .build();

        // mock ticketRepository "findById" operation
        when(ticketRepository.findById(any(String.class))).thenReturn(Optional.of(ticket));

        // act
        SingleTicketResponse response = (SingleTicketResponse) ticketService.getTicketById(ticket.getId());

        // assert
        assertNotNull(response);
        assertEquals(response.getTicket(), ticket);
        verify(ticketRepository).findById(ticket.getId());
    }

    @Test
    void getTicketById_TicketNotFound_InvalidTicketIdExceptionThrown() {

        // arrange
        Ticket ticket = Ticket.builder()
                .id("1234")
                .eventId("1234")
                .userIdAttending("1234")
                .userIdBuyer("1234")
                .isSurpriseTicket(false)
                .build();

        // mock ticketRepository "findById" operation
        when(ticketRepository.findById(any(String.class))).thenReturn(Optional.empty());

        InvalidTicketIdException e = assertThrows(InvalidTicketIdException.class, () -> {
            //act
            ticketService.getTicketById(ticket.getId());
        });

        // assert
        assertEquals("Ticket with ticketId: 1234 does not exists", e.getMessage());
        verify(ticketRepository).findById(ticket.getId());
    }

    @Test
    void getTicketsByUser_UserFound_ReturnTicketResponse() {

        // arrange
        Ticket ticket = Ticket.builder()
                .id("1234")
                .eventId("1234")
                .userIdAttending("1234")
                .userIdBuyer("1234")
                .isSurpriseTicket(false)
                .build();

        List<Ticket> ticketList = List.of(ticket);

        User user = User.builder()
                .id("1234")
                .username("testUser")
                .build();

        // mock userRepository "findByUsername" operation
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user));

        // mock ticketRepository "findAllByUserIdAttending" operation
        when(ticketRepository.findAllByUserIdAttending(any(String.class))).thenReturn(ticketList);

        //act
        TicketResponse response = (TicketResponse) ticketService.getTicketsByUser(user.getUsername());

        // assert
        assertNotNull(response);
        assertIterableEquals(ticketList, response.getTickets());
        verify(userRepository).findByUsername(user.getUsername());
        verify(ticketRepository).findAllByUserIdAttending(user.getId());
    }

    @Test
    void getTicketsByUser_UserNotFound_InvalidTokenExceptionThrown() {

        // arrange
        /* Ticket ticket = Ticket.builder()
                .id("1234")
                .eventId("1234")
                .userIdAttending("1234")
                .userIdBuyer("1234")
                .isSurpriseTicket(false)
                .build(); */

        User user = User.builder()
                .id("1234")
                .username("testUser")
                .build();

        // mock userRepository "findByUsername" operation
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

        //act
        InvalidTokenException e = assertThrows(InvalidTokenException.class, () -> {
            // act
            ticketService.getTicketsByUser(user.getUsername());
        });

        // assert
        assertEquals("Token is not valid", e.getMessage());
        verify(userRepository).findByUsername(user.getUsername());
    }

    @Test
    void getTicketByEvent_EventFound_ReturnTicketResponse() {

        // arrange
        Ticket ticket = Ticket.builder()
                .id("1234")
                .eventId("1234")
                .userIdAttending("1234")
                .userIdBuyer("1234")
                .isSurpriseTicket(false)
                .build();

        List<Ticket> ticketList = List.of(ticket);

        Event event = Event.builder()
                .id("1234")
                .build();

        // mock eventRepository "findById" operation
        when(eventRepository.findById(any(String.class))).thenReturn(Optional.of(event));

        // mock ticketRepository "findByEventId"
        when(ticketRepository.findByEventId(any(String.class))).thenReturn(ticketList);

        // act
        TicketResponse response = (TicketResponse) ticketService.getTicketByEvent(event.getId());

        // assert
        assertNotNull(response);
        assertIterableEquals(ticketList, response.getTickets());
        verify(eventRepository).findById(event.getId());
        verify(ticketRepository).findByEventId(event.getId());
    }

    @Test
    void getTicketByEvent_EventNotFound_InvalidEventId() {

        // arrange
        /* Ticket ticket = Ticket.builder()
                .id("1234")
                .eventId("1234")
                .userIdAttending("1234")
                .userIdBuyer("1234")
                .isSurpriseTicket(false)
                .build(); */

        Event event = Event.builder()
                .id("1234")
                .build();

        // mock eventRepository "findById" operation
        when(eventRepository.findById(any(String.class))).thenReturn(Optional.empty());

        InvalidEventIdException e = assertThrows(InvalidEventIdException.class, () -> {
            // act
            ticketService.getTicketByEvent(event.getId());
        });

        // assert
        assertEquals("Event with eventId: 1234 does not exists", e.getMessage());
        verify(eventRepository).findById(event.getId());
    }
}
