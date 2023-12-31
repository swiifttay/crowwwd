package com.cs203.g1t4.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cs203.g1t4.backend.service.serviceImpl.TicketServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

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
import com.cs203.g1t4.backend.repository.EventRepository;
import com.cs203.g1t4.backend.repository.TicketRepository;
import com.cs203.g1t4.backend.repository.UserRepository;
import com.cs203.g1t4.backend.service.services.TicketService;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private TicketServiceImpl ticketService;

    @Test
    void createTicket_UserFoundLessThan4Purchased_ReturnSuccessResponse() {

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
                .isSurpriseTicket(false)
                .build();

        ArgumentCaptor<Ticket> ticketCaptor = ArgumentCaptor.forClass(Ticket.class);

        // mock userRepository "findByUsername" operation
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user));

        // mock ticketRepository "findByEventIdAndUserIdAttending" operation
        when(ticketRepository.findAllByEventIdAndUserIdBuyer(any(String.class), any(String.class))).thenReturn(List.of());

        // mock "save" operation
        when(ticketRepository.save(ticketCaptor.capture())).thenAnswer(invocation -> {
            return ticketCaptor.getValue();
        });

        // act
        Response response = ticketService.createTicket(ticketRequest, user.getUsername());

        // assert
        assertTrue(response instanceof SingleTicketResponse);
        SingleTicketResponse singleTicketResponse = (SingleTicketResponse) response;
        assertEquals(ticket, singleTicketResponse.getTicket());
        verify(userRepository).findByUsername(user.getUsername());
        verify(ticketRepository).findAllByEventIdAndUserIdBuyer(ticket.getEventId(), user.getId());
        verify(ticketRepository).save(ticketCaptor.getValue());
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
    void createTicket_UserFoundMoreThan4Purchased_DuplicateTicketExceptionThrown() {

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

        // simulate 4 purchased tickets found
        List<Ticket> ticketList = new ArrayList<>();
        ticketList.add(ticket);
        ticketList.add(ticket);
        ticketList.add(ticket);
        ticketList.add(ticket);

        // mock userRepository "findByUsername" operation
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user));

        // mock ticketRepository "findByEventIdAndUserIdAttending" operation
        when(ticketRepository.findAllByEventIdAndUserIdBuyer(any(String.class), any(String.class))).thenReturn(ticketList);

        DuplicateTicketException e = assertThrows(DuplicateTicketException.class, () -> {
            // act
            ticketService.createTicket(ticketRequest, user.getUsername());
        });

        // assert
        assertEquals("User 1234 already bought a maximum of 4 tickets for 1234 event.", e.getMessage());
        verify(userRepository).findByUsername(user.getUsername());
        verify(ticketRepository).findAllByEventIdAndUserIdBuyer(ticket.getEventId(), user.getId());
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
        assertEquals("Event with eventId: 1234 does not exist", e.getMessage());
        verify(eventRepository).findById(event.getId());
    }
}
