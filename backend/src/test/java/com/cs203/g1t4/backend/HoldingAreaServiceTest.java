package com.cs203.g1t4.backend;

import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.queue.QueueResponse;
import com.cs203.g1t4.backend.models.User;
import com.cs203.g1t4.backend.models.event.Event;
import com.cs203.g1t4.backend.models.exceptions.InvalidPurchasingTimeException;
import com.cs203.g1t4.backend.models.queue.QueueStatus;
import com.cs203.g1t4.backend.models.queue.QueueingStatusValues;
import com.cs203.g1t4.backend.repository.*;
import com.cs203.g1t4.backend.service.serviceImpl.HoldingAreaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HoldingAreaServiceTest {

    @Mock
    private HoldingAreaRepository holdingAreaRepository;
    @Mock
    private FanRecordRepository fanRecordRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private EventRepository eventRepository;
    @Mock
    private QueueStatusRepository queueStatusRepository;
    @InjectMocks
    private HoldingAreaServiceImpl holdingAreaService;

    private User existingUser;
    private ArrayList<LocalDateTime> dateTimeList;
    private ArrayList<LocalDateTime> validTicketSalesDateTimeList;
    private Event validToPurchaseEvent;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        existingUser = User.builder()
                .id("1234")
                .username("user")
                .build();

        now = LocalDateTime.now();

        dateTimeList = new ArrayList<>();
        dateTimeList.add(now.plusMinutes(60));

        validTicketSalesDateTimeList = new ArrayList<>();
        validTicketSalesDateTimeList.add(now.minusMinutes(10));

        validToPurchaseEvent = Event.builder()
                .id("1234")
                .name("The Eras Tour")
                .dates(dateTimeList)
                .ticketSalesDate(validTicketSalesDateTimeList)
                .build();
    }

    @Test
    void enterHoldingArea_ValidPurchasingTimeUserInQueueAlready_ReturnQueueResponse() {

        // arrange
        QueueStatus queueStatus = QueueStatus.builder()
                .id("1234")
                .userId(existingUser.getId())
                .eventId("1234")
                .queueStatus(QueueingStatusValues.OK)
                .build();

        // stubbing
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(existingUser));
        when(eventRepository.findById(any(String.class))).thenReturn(Optional.of(validToPurchaseEvent));
        when(queueStatusRepository.findQueueByUserIdAndEventId(any(String.class), any(String.class))).thenReturn(Optional.of(queueStatus));

        // act
        Response response = holdingAreaService.enterHoldingArea(existingUser.getUsername(), validToPurchaseEvent.getId());

        // assert
        assertTrue(response instanceof QueueResponse);
        QueueResponse queueResponse = (QueueResponse) response;
        assertEquals(queueStatus.getQueueStatus().getStatusName(), queueResponse.getQueueingStatus());
        verify(userRepository).findByUsername(existingUser.getUsername());
        verify(eventRepository).findById(validToPurchaseEvent.getId());
        verify(queueStatusRepository).findQueueByUserIdAndEventId(existingUser.getId(), validToPurchaseEvent.getId());
    }

    @Test
    void enterHoldingArea_InvalidPurchasingTime_ReturnInvalidPurchasingTimeException() {

        // arrange
        ArrayList<LocalDateTime> invalidTicketSalesDateTimeList = new ArrayList<>();
        invalidTicketSalesDateTimeList.add(now.plusMinutes(60));

        Event invalidToPurchaseEvent = Event.builder()
                .id("1234")
                .name("The Eras Tour")
                .dates(dateTimeList)
                .ticketSalesDate(invalidTicketSalesDateTimeList)
                .build();

        // stubbing
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(existingUser));
        when(eventRepository.findById(any(String.class))).thenReturn(Optional.of(invalidToPurchaseEvent));

        // act
        InvalidPurchasingTimeException e = assertThrows(InvalidPurchasingTimeException.class, () -> {
            holdingAreaService.enterHoldingArea(existingUser.getUsername(), invalidToPurchaseEvent.getId());
        });

        // assert
        assertEquals("The Eras Tour is not open for purchasing tickets", e.getMessage());
        verify(userRepository).findByUsername(existingUser.getUsername());
        verify(eventRepository).findById(invalidToPurchaseEvent.getId());
    }
}
