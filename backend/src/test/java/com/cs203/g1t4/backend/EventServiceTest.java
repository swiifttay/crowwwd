package com.cs203.g1t4.backend;

import com.cs203.g1t4.backend.data.request.event.EventRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.artist.SingleArtistResponse;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.data.response.event.SingleEventResponse;
import com.cs203.g1t4.backend.models.Artist;
import com.cs203.g1t4.backend.models.event.Event;
import com.cs203.g1t4.backend.models.event.OutputEvent;
import com.cs203.g1t4.backend.models.exceptions.DuplicatedEventException;
import com.cs203.g1t4.backend.models.exceptions.InvalidArtistIdException;
import com.cs203.g1t4.backend.models.exceptions.InvalidEventIdException;
import com.cs203.g1t4.backend.repository.ArtistRepository;
import com.cs203.g1t4.backend.repository.EventRepository;
import com.cs203.g1t4.backend.service.EventService;
import com.cs203.g1t4.backend.service.S3Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private ArtistRepository artistRepository;

    @Mock
    private S3Service s3Service;

    @Mock
    MultipartFile image;

    @InjectMocks
    private EventService eventService;

    Event customEvent;

    @BeforeEach
    void setUp() {

        customEvent = Event.builder()
                .id("1234")
                .name("The Eras Tour")
                .eventImageName("theerastourimage")
                .description("description")
                .dates(new ArrayList<LocalDateTime>())
                .venue("National Stadium")
                .categories(new ArrayList<String>())
                .artistId("1234")
                .seatingImagePlan("theerastourseatingplanimage")
                .ticketSalesDate(new ArrayList<LocalDateTime>())
                .build();

        ReflectionTestUtils.setField(eventService, "bucketName", "your_bucket_name");
    }

    @Test
    void addEvent_NewEvent_ReturnSuccessResponse() {

        // arrange
        EventRequest eventRequest = EventRequest.builder()
                .name("Music of Spheres Tour")
                .eventImageName("musicofspherestourimage")
                .description("description")
                .dates(new String[0])
                .venue("National Stadium")
                .categories(new String[0])
                .artistId("1235")
                .seatingImagePlan("musicofspherestourseatingplanimage")
                .ticketSalesDate(new String[0])
                .build();

        Artist artist = Artist.builder()
                .id("1235")
                .name("Coldplay")
                .build();

        ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);

        // mock artistRepository "findById" operation
        when(artistRepository.findById(any(String.class))).thenReturn(Optional.of(artist));

        // mock eventRepository "findByArtistIdAndName" operation
        when(eventRepository.findByArtistIdAndName(any(String.class), any(String.class))).thenReturn(Optional.empty());

        // mock eventRepository "save" operation
        when(eventRepository.save(eventCaptor.capture())).thenAnswer(invocation -> {
            Event savedEvent = eventCaptor.getValue();
            savedEvent.setId("1235");
            return savedEvent;
        });

        // act
        Response response = eventService.addEvent(eventRequest);

        // assert
        assertTrue(response instanceof SuccessResponse);
        assertEquals("Event has been created successfully", ((SuccessResponse) response).getResponse());
        verify(artistRepository).findById(artist.getId());
        verify(eventRepository).findByArtistIdAndName(artist.getId(),eventRequest.getName());
        verify(eventRepository).save(eventCaptor.getValue());
    }

    @Test
    void addEvent_ArtistNotFound_ReturnInvalidArtistIdException() {

        // arrange
        EventRequest eventRequest = EventRequest.builder()
                .name("Music of Spheres Tour")
                .eventImageName("musicofspherestourimage")
                .description("description")
                .dates(new String[0])
                .venue("National Stadium")
                .categories(new String[0])
                .artistId("1235")
                .seatingImagePlan("musicofspherestourseatingplanimage")
                .ticketSalesDate(new String[0])
                .build();

        Artist artist = Artist.builder()
                .id("1235")
                .name("Coldplay")
                .build();

        // mock artistRepository "findById" operation
        when(artistRepository.findById(any(String.class))).thenReturn(Optional.empty());

        InvalidArtistIdException e = assertThrows(InvalidArtistIdException.class, () -> {
            eventService.addEvent(eventRequest);
        });

        assertEquals("Artist with artistId: 1235 does not exist", e.getMessage());
        verify(artistRepository).findById(artist.getId());
    }

    @Test
    void addEvent_DuplicateEvent_ReturnDuplicatedEventException() {
        // arrange
        EventRequest eventRequest = EventRequest.builder()
                .name("The Eras Tour")
                .eventImageName("theerastourimage")
                .description("description")
                .dates(new String[0])
                .venue("National Stadium")
                .categories(new String[0])
                .artistId("1234")
                .seatingImagePlan("theerastourseatingplanimage")
                .ticketSalesDate(new String[0])
                .build();

        Artist artist = Artist.builder()
                .id("1234")
                .name("Taylor Swift")
                .build();

        // mock artistRepository "findById" operation
        when(artistRepository.findById(any(String.class))).thenReturn(Optional.of(artist));

        //mock eventRepository "findByArtistIdAndName" operation
        when(eventRepository.findByArtistIdAndName(any(String.class), any(String.class))).thenReturn(Optional.of(customEvent));

        DuplicatedEventException e = assertThrows(DuplicatedEventException.class, () -> {
            eventService.addEvent(eventRequest);
        });

        assertEquals("Artist: 1234 already has an event The Eras Tour", e.getMessage());
        verify(artistRepository).findById(artist.getId());
        verify(eventRepository).findByArtistIdAndName(artist.getId(), customEvent.getName());
    }

    @Test
    void deleteEvent_ExistingEvent_ReturnSingleEventResponse() {

        // arrange
        Artist artist = Artist.builder()
                .id("1234")
                .name("Taylor Swift")
                .build();

        OutputEvent outputEvent = customEvent.returnOutputEvent(artist);

        // mock eventRepository "findById" operation
        when(eventRepository.findById(any(String.class))).thenReturn(Optional.of(customEvent));

        // mock artistRepository "findById" operation
        when(artistRepository.findById(any(String.class))).thenReturn(Optional.of(artist));

        // mock eventRepository "deleteById" operation
        doNothing().when(eventRepository).deleteById(any(String.class));

        // act
        Response response =  eventService.deleteEventById(customEvent.getId());

        // assert
        assertTrue(response instanceof SingleEventResponse);
        SingleEventResponse singleEventResponse = (SingleEventResponse) response;
        assertEquals(outputEvent, singleEventResponse.getOutputEvent());

        verify(eventRepository).findById(customEvent.getId());
        verify(artistRepository).findById(artist.getId());
        verify(eventRepository).deleteById(customEvent.getId());
    }

    @Test
    void deleteEvent_InvalidEventId_ReturnInvalidEventIdException() {

        // arrange
        String invalidEventId = "1236";

        // mock eventRepository "findById" operation
        when(eventRepository.findById(any(String.class))).thenReturn(Optional.empty());

        // act
        InvalidEventIdException e = assertThrows(InvalidEventIdException.class, () -> {
            eventService.deleteEventById(invalidEventId);
        });

        // assert
        assertEquals("Event with eventId: 1236 does not exists", e.getMessage());
        verify(eventRepository).findById(invalidEventId);
    }

    @Test
    void deleteEvent_InvalidArtistId_ReturnInvalidArtistIdException() {

        // arrange
        Artist artist = Artist.builder()
                .id("1234")
                .name("Taylor Swift")
                .build();

        // mock eventRepository "findById" operation
        when(eventRepository.findById(any(String.class))).thenReturn(Optional.of(customEvent));

        // mock artistRepository "findById" operation
        when(artistRepository.findById(any(String.class))).thenReturn(Optional.empty());

        // act
        InvalidArtistIdException e = assertThrows(InvalidArtistIdException.class, () -> {
            eventService.deleteEventById(customEvent.getId());
        });

        // assert
        assertEquals("Artist with artistId: 1234 does not exist", e.getMessage());
        verify(eventRepository).findById(customEvent.getId());
        verify(artistRepository).findById(artist.getId());
    }

    @Test
    void updateEventById_ValidUpdates_ReturnSingleEventResponse() {

        // arrange
        EventRequest updatedEventRequest = EventRequest.builder()
                .name("The Eras Tour Updated")
                .eventImageName("theerastourimage")
                .description("description")
                .dates(new String[0])
                .venue("National Stadium")
                .categories(new String[0])
                .artistId("1234")
                .seatingImagePlan("theerastourseatingplanimage")
                .ticketSalesDate(new String[0])
                .build();

        Event updatedEvent = Event.builder()
                .id("1234")
                .name("The Eras Tour Updated")
                .eventImageName("theerastourimage")
                .description("description")
                .dates(new ArrayList<LocalDateTime>())
                .venue("National Stadium")
                .categories(new ArrayList<String>())
                .artistId("1234")
                .seatingImagePlan("theerastourseatingplanimage")
                .ticketSalesDate(new ArrayList<LocalDateTime>())
                .build();

        Artist artist = Artist.builder()
                .id("1234")
                .name("Taylor Swift")
                .build();

        OutputEvent outputEvent = updatedEvent.returnOutputEvent(artist);

        System.out.println(outputEvent.getName());

        ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);

        // mock eventRepository "findById" operation
        when(eventRepository.findById(any(String.class))).thenReturn(Optional.of(customEvent));

        // mock artistRepository "findById" operation
        when(artistRepository.findById(any(String.class))).thenReturn(Optional.of(artist));

        // mock eventRepository "findById" operation
        when(eventRepository.save(eventCaptor.capture())).thenAnswer(invocation -> {
            Event savedEvent = eventCaptor.getValue();
            return savedEvent;
        });

        //act
        Response response = eventService.updateEventById(customEvent.getId(), updatedEventRequest);

        // assert
        assertTrue(response instanceof SingleEventResponse);
        SingleEventResponse singleEventResponse = (SingleEventResponse) response;
        assertEquals(outputEvent, singleEventResponse.getOutputEvent());

        verify(eventRepository.findById(customEvent.getId()));
        verify(artistRepository).findById(artist.getId());
        verify(eventRepository).save(eventCaptor.getValue());
    }
}

