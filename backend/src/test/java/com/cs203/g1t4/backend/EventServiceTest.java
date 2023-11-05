package com.cs203.g1t4.backend;

import com.cs203.g1t4.backend.data.request.event.EventRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.data.response.event.SingleFullEventResponse;
import com.cs203.g1t4.backend.models.Artist;
import com.cs203.g1t4.backend.models.Venue;
import com.cs203.g1t4.backend.models.event.Event;
import com.cs203.g1t4.backend.models.event.FullEvent;
import com.cs203.g1t4.backend.models.exceptions.DuplicatedEventException;
import com.cs203.g1t4.backend.models.exceptions.InvalidArtistIdException;
import com.cs203.g1t4.backend.models.exceptions.InvalidEventIdException;
import com.cs203.g1t4.backend.repository.ArtistRepository;
import com.cs203.g1t4.backend.repository.EventRepository;
import com.cs203.g1t4.backend.repository.VenueRepository;
import com.cs203.g1t4.backend.service.serviceImpl.EventServiceImpl;
import com.cs203.g1t4.backend.service.services.S3Service;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.annotation.Id;
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
    private VenueRepository venueRepository;

    @Mock
    private S3Service s3Service;

    @Mock
    MultipartFile image;

    @InjectMocks
    private EventServiceImpl eventService;

    Event customEvent;

    Artist customArtist;

    Venue venue;


    @BeforeEach
    void setUp() {

        // arrange
        customEvent = Event.builder()
                .id("1234")
                .name("The Eras Tour")
                .eventImageName("theerastourimage")
                .description("description")
                .dates(new ArrayList<LocalDateTime>())
                .venue("1234")
                .categories(new ArrayList<String>())
                .artistId("1234")
                .seatingImagePlan("theerastourseatingplanimage")
                .ticketSalesDate(new ArrayList<LocalDateTime>())
                .build();

        customArtist = Artist.builder()
                .id("1234")
                .name("Taylor Swift")
                .build();

        venue = Venue.builder()
                .id("1234")
                .locationName("National Stadium")
                .address("Singapore National Stadium Road")
                .postalCode("S123456")
                .description("Singapores beautiful national stadium")
                .venueImageName("NationalStadiumImage")
                .build();

        image = mock(MultipartFile.class);

        ReflectionTestUtils.setField(eventService, "bucketName", "your_bucket_name");
    }

    @Test
    void addEvent_NewEvent_ReturnSuccessResponse() {

        // arrange
        EventRequest eventRequest = EventRequest.builder()
                .name("Music of Spheres Tour")
                .eventImageName("musicofspherestourimage")
                .description("description")
                .dates(new String[]{"2023-11-01T00:00:00", "2023-11-02T00:00:00"})
                .venue(venue.getId())
                .categories(new String[0])
                .artistId("1234")
                .seatingImagePlan("musicofspherestourseatingplanimage")
                .ticketSalesDate(new String[]{"2023-11-01T00:00:00", "2023-11-02T00:00:00"})
                .build();

        ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);

        // mock venueRepository "findById" operation
        when(venueRepository.findById(any(String.class))).thenReturn(Optional.of(venue));

        // mock artistRepository "findById" operation
        when(artistRepository.findById(any(String.class))).thenReturn(Optional.of(customArtist));

        // mock eventRepository "findByArtistIdAndName" operation
        when(eventRepository.findByArtistIdAndName(any(String.class), any(String.class))).thenReturn(Optional.empty());

        // mock eventRepository "save" operation
        when(eventRepository.save(eventCaptor.capture())).thenAnswer(invocation -> {
            Event savedEvent = eventCaptor.getValue();
            savedEvent.setId("1235");
            return savedEvent;
        });

        // act
        Response response = eventService.addFullEvent(eventRequest, image);

        // assert
        assertTrue(response instanceof SuccessResponse);
        assertEquals("Event has been created successfully", ((SuccessResponse) response).getResponse());
        verify(artistRepository, times(2)).findById(customArtist.getId());
        verify(eventRepository).findByArtistIdAndName(customArtist.getId(),eventRequest.getName());
        verify(eventRepository).save(eventCaptor.getValue());
    }

    @Test
    void addEvent_ArtistNotFound_ReturnInvalidArtistIdException() {

        // arrange
        String invalidArtistId = "1235";

        EventRequest eventRequest = EventRequest.builder()
                .name("Music of Spheres Tour")
                .eventImageName("musicofspherestourimage")
                .description("description")
                .dates(new String[]{"2023-11-01T00:00:00", "2023-11-02T00:00:00"})
                .venue(venue.getId())
                .categories(new String[0])
                .artistId(invalidArtistId)
                .seatingImagePlan("musicofspherestourseatingplanimage")
                .ticketSalesDate(new String[]{"2023-11-01T00:00:00", "2023-11-02T00:00:00"})
                .build();

        // mock artistRepository "findById" operation
        when(artistRepository.findById(any(String.class))).thenReturn(Optional.empty());

        InvalidArtistIdException e = assertThrows(InvalidArtistIdException.class, () -> {
            eventService.addFullEvent(eventRequest, image);
        });

        assertEquals("Artist with artistId: 1235 does not exist", e.getMessage());
        verify(artistRepository).findById(invalidArtistId);
    }

    @Test
    void addEvent_DuplicateEvent_ReturnDuplicatedEventException() {
        // arrange
        EventRequest eventRequest = EventRequest.builder()
                .name("The Eras Tour")
                .eventImageName("theerastourimage")
                .description("description")
                .dates(new String[0])
                .venue(venue.getId())
                .categories(new String[0])
                .artistId("1234")
                .seatingImagePlan("theerastourseatingplanimage")
                .ticketSalesDate(new String[0])
                .build();

        // mock venueRepository "findById" operation
        when(venueRepository.findById(any(String.class))).thenReturn(Optional.of(venue));

        // mock artistRepository "findById" operation
        when(artistRepository.findById(any(String.class))).thenReturn(Optional.of(customArtist));

        //mock eventRepository "findByArtistIdAndName" operation
        when(eventRepository.findByArtistIdAndName(any(String.class), any(String.class))).thenReturn(Optional.of(customEvent));

        DuplicatedEventException e = assertThrows(DuplicatedEventException.class, () -> {
            eventService.addFullEvent(eventRequest, image);
        });

        assertEquals("Artist: 1234 already has an event The Eras Tour", e.getMessage());
        verify(artistRepository).findById(customArtist.getId());
        verify(eventRepository).findByArtistIdAndName(customArtist.getId(), customEvent.getName());
    }

    @Test
    void deleteEvent_ExistingEvent_ReturnSingleEventResponse() {

        // arrange
        FullEvent customFullEvent = FullEvent.builder()
                        .eventId("1234")
                        .name("The Eras Tour")
                        .eventImageName("theerastourimage")
                        .description("description")
                        .dates(new ArrayList<String>())
                        .venue(venue)
                        .categories(new ArrayList<String>())
                        .artist(customArtist)
                        .seatingImagePlan("theerastourseatingplanimage")
                        .ticketSalesDate(new ArrayList<String>())
                        .build();

        // mock eventRepository "findById" operation
        when(eventRepository.findById(any(String.class))).thenReturn(Optional.of(customEvent));

        // mock artistRepository "findById" operation
        when(artistRepository.findById(any(String.class))).thenReturn(Optional.of(customArtist));

        // mock venueRepository "findById" operation
        when(venueRepository.findById(any(String.class))).thenReturn(Optional.of(venue));

        // mock eventRepository "deleteById" operation
        doNothing().when(eventRepository).deleteById(any(String.class));

        // act
        Response response =  eventService.deleteFullEventById(customEvent.getId());

        // assert
        assertTrue(response instanceof SingleFullEventResponse);
        SingleFullEventResponse singleEventResponse = (SingleFullEventResponse) response;
        assertEquals(customFullEvent, singleEventResponse.getFullEvent());

        verify(eventRepository).findById(customEvent.getId());
        verify(artistRepository).findById(customArtist.getId());
        verify(venueRepository).findById(venue.getId());
    }

    @Test
    void deleteEvent_InvalidEventId_ReturnInvalidEventIdException() {

        // arrange
        String invalidEventId = "1236";

        // mock eventRepository "findById" operation
        when(eventRepository.findById(any(String.class))).thenReturn(Optional.empty());

        // act
        InvalidEventIdException e = assertThrows(InvalidEventIdException.class, () -> {
            eventService.deleteFullEventById(invalidEventId);
        });

        // assert
        assertEquals("Event with eventId: 1236 does not exist", e.getMessage());
        verify(eventRepository).findById(invalidEventId);
    }

    @Test
    void deleteEvent_InvalidArtistId_ReturnInvalidArtistIdException() {

        // mock eventRepository "findById" operation
        when(eventRepository.findById(any(String.class))).thenReturn(Optional.of(customEvent));

        // mock artistRepository "findById" operation
        when(artistRepository.findById(any(String.class))).thenReturn(Optional.empty());

        // act
        InvalidArtistIdException e = assertThrows(InvalidArtistIdException.class, () -> {
            eventService.deleteFullEventById(customEvent.getId());
        });

        // assert
        assertEquals("Artist with artistId: 1234 does not exist", e.getMessage());
        verify(eventRepository).findById(customEvent.getId());
        verify(artistRepository).findById(customArtist.getId());
    }

    @Test
    void updateEventById_ValidUpdates_ReturnSingleEventResponse() {

        // arrange
        EventRequest updatedEventRequest = EventRequest.builder()
                .name("The Eras Tour Updated")
                .eventImageName("theerastourimage")
                .description("description")
                .dates(new String[0])
                .venue(venue.getId())
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

        FullEvent fullevent = customEvent.returnFullEvent(artist, venue);
        ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);

        // mock venueRepository "findById" operation
        when(venueRepository.findById(any(String.class))).thenReturn(Optional.of(venue));
        // mock eventRepository "findById" operation
        when(eventRepository.findById(any(String.class))).thenReturn(Optional.of(customEvent)).thenReturn(Optional.of(updatedEvent));

        // mock artistRepository "findById" operation
        when(artistRepository.findById(any(String.class))).thenReturn(Optional.of(artist)).thenReturn(Optional.of(artist));

        // mock eventRepository "findById" operation
        when(eventRepository.save(eventCaptor.capture())).thenAnswer(invocation -> {
            return eventCaptor.getValue();
        });

        //act
        Response response = eventService.updateFullEventById(customEvent.getId(), updatedEventRequest,  image);

        // assert
        assertTrue(response instanceof SingleFullEventResponse);
        SingleFullEventResponse singleEventResponse = (SingleFullEventResponse) response;
        assertEquals(fullevent, singleEventResponse.getFullEvent());

        verify(eventRepository, atLeastOnce()).findById(customEvent.getId());
        verify(artistRepository, atLeastOnce()).findById(artist.getId());
        verify(eventRepository).save(eventCaptor.getValue());
    }

//    @Test
//    void findEventById_ExistingEvent_ReturnSingleEventResponse() {
//
//        // arrange
//        OutputEvent outputEvent = customEvent.returnOutputEvent(customArtist);
//
//        // mock eventRepository "findById" operation
//        when(eventRepository.findById(any(String.class))).thenReturn(Optional.of(customEvent));
//
//        // mock artistRepository "findById" operation
//        when(artistRepository.findById(any(String.class))).thenReturn(Optional.of(customArtist));
//
//        // act
//        Response response = eventService.findEventById(customEvent.getId());
//
//        // assert
//        assertTrue(response instanceof SingleEventResponse);
//        SingleEventResponse singleEventResponse = (SingleEventResponse) response;
//        assertEquals(outputEvent, singleEventResponse.getOutputEvent());
//        verify(eventRepository).findById(customEvent.getId());
//        verify(artistRepository).findById(customArtist.getId());
//    }
//
//    @Test
//    void findEventById_EventNotFound_ReturnInvalidEventIdException() {
//
//        // arrange
//        String invalidEventId = "1236";
//
//        // mock eventRepository "findById" operation
//        when(eventRepository.findById(any(String.class))).thenReturn(Optional.empty());
//
//        // act
//        InvalidEventIdException e = assertThrows(InvalidEventIdException.class, () -> {
//            eventService.findEventById(invalidEventId);
//        });
//
//        // assert
//        assertEquals("Event with eventId: 1236 does not exist", e.getMessage());
//        verify(eventRepository).findById(invalidEventId);
//    }
//
//    @Test
//    void findEventById_ArtistNotFound_ReturnInvalidEventIdException() {
//
//        // mock eventRepository "findById" operation
//        when(eventRepository.findById(any(String.class))).thenReturn(Optional.of(customEvent));
//
//        // mock artistRepository "findById" operation
//        when(artistRepository.findById(any(String.class))).thenReturn(Optional.empty());
//
//        // act
//        InvalidArtistIdException e = assertThrows(InvalidArtistIdException.class, () -> {
//            eventService.findEventById(customEvent.getArtistId());
//        });
//
//        // assert
//        assertEquals("Artist with artistId: 1234 does not exist", e.getMessage());
//        verify(eventRepository).findById(customEvent.getArtistId());
//    }
//
//    @Test
//    void getAllEventsAfterToday_EventsAfterTodayFound_Return_EventResponse() {
//
//        // arrange
//        LocalDateTime currDateTime = LocalDateTime.now();
//
//        LocalDateTime futureDateTime = currDateTime.plusDays(30L);
//
//        List<LocalDateTime> dateList = new ArrayList<>();
//        dateList.add(futureDateTime);
//
//        Event futureEvent = Event.builder()
//                .id("1234")
//                .name("The Eras Tour")
//                .eventImageName("theerastourimage")
//                .description("description")
//                .dates(dateList)
//                .venue("National Stadium")
//                .categories(new ArrayList<String>())
//                .artistId("1234")
//                .seatingImagePlan("theerastourseatingplanimage")
//                .ticketSalesDate(new ArrayList<LocalDateTime>())
//                .build();
//
//        OutputEvent outputEvent = futureEvent.returnOutputEvent(customArtist);
//        outputEvent.setEventImageURL("https://%s.s3.ap-southeast-1.amazonaws.com/event-images/%s/%s"
//                .formatted("your_bucket_name", "1234", "theerastourimage"));
//
//        List<Event> events = new ArrayList<>();
//        events.add(futureEvent);
//
//        List<OutputEvent> outputEvents = new ArrayList<>();
//        outputEvents.add(outputEvent);
//
//        ArgumentCaptor<LocalDateTime> timeArgumentCaptor = ArgumentCaptor.forClass(LocalDateTime.class);
//
//        // mock eventRepository "findByDatesGreaterThan" operation
//        when(eventRepository.findByDatesGreaterThan(any(LocalDateTime.class))).thenReturn(events);
//
//        // mock eventRepository "findById" operation
//        when(eventRepository.findById(any(String.class))).thenReturn(Optional.of(futureEvent));
//
//        // mock artistRepository "findById" operation
//        when(artistRepository.findById(any(String.class))).thenReturn(Optional.of(customArtist));
//
//        // act
//        Response response = eventService.getAllEventsAfterToday();
//
//        // assert
//        assertTrue(response instanceof EventResponse);
//        EventResponse eventResponse = (EventResponse) response;
//        assertEquals(outputEvents, eventResponse.getEvents());
//        verify(eventRepository).findByDatesGreaterThan(timeArgumentCaptor.capture());
//        verify(eventRepository).findById(futureEvent.getId());
//        verify(artistRepository).findById(customArtist.getId());
//    }
}

