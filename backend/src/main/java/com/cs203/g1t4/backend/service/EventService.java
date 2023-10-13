package com.cs203.g1t4.backend.service;

import com.cs203.g1t4.backend.data.request.event.EventRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.data.response.event.ExploreEventsResponse;
import com.cs203.g1t4.backend.data.response.event.SingleDetailsEventResponse;
import com.cs203.g1t4.backend.data.response.event.SingleFullEventResponse;
import com.cs203.g1t4.backend.models.Artist;
import com.cs203.g1t4.backend.models.Venue;
import com.cs203.g1t4.backend.models.event.DetailsEvent;
import com.cs203.g1t4.backend.models.event.Event;
import com.cs203.g1t4.backend.models.event.ExploreEvent;
import com.cs203.g1t4.backend.models.event.FullEvent;
import com.cs203.g1t4.backend.models.exceptions.DuplicatedEventException;
import com.cs203.g1t4.backend.models.exceptions.InvalidArtistIdException;
import com.cs203.g1t4.backend.models.exceptions.InvalidEventIdException;
import com.cs203.g1t4.backend.models.exceptions.InvalidVenueException;
import com.cs203.g1t4.backend.repository.ArtistRepository;
import com.cs203.g1t4.backend.repository.EventRepository;
import com.cs203.g1t4.backend.repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final ArtistRepository artistRepository;
    private final VenueRepository venueRepository;
    private final S3Service s3Service;

    @Value("${aws.bucket.name}")
    private String bucketName;

    // Main Service Methods
    public Response addFullEvent(EventRequest request, MultipartFile image) {

        // Creates newEvent using private method
        // getEventClassFromRequest(EventRequest request, Event oldEvent)
        Event event = getEventClassFromRequest(request, null);

        //Saves event into database
        eventRepository.save(event);

        // Get the event image name
        String eventImageName = image.getOriginalFilename();

        // Put the image into the bucket
        s3Service.putObject(
                bucketName,
                "event-images/%s/%s".formatted(event.getId(), eventImageName),
                image
        );

        //If Everything goes smoothly, SuccessResponse will be created
        return SuccessResponse.builder()
                .response("Event has been created successfully")
                .build();
    }

    public Response deleteFullEventById(String eventId) {

        //Checks if there is an event with the specified eventID in the repository
        //If there are any exceptions, it will be propagated out
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new InvalidEventIdException(eventId));

        //If event can be found, delete it from repository
        eventRepository.deleteById(eventId);

        //If Everything goes smoothly, return the event in SingleFullEventResponse
        return SingleFullEventResponse.builder()
                .fullEvent(getFullEventFromEvent(event))
                .build();
    }

    public Response updateFullEventById(String eventId, EventRequest request, MultipartFile image) {

        //Checks if there is an event with the specified eventID in the repository
        //If event cannot be found, throws new InvalidEventIdException if no such event found
        Event oldEvent = eventRepository.findById(eventId).orElseThrow(() -> new InvalidEventIdException(eventId));

        //Creates newEvent using private method getEventClassFromRequest(EventRequest request, Event oldEvent)
        Event newEvent = getEventClassFromRequest(request, oldEvent);

        eventRepository.save(newEvent);

        if (image != null || !image.isEmpty() ) {
            // Get the event image name
            String eventImageName = oldEvent.getEventImageName();


            // Put the image into the bucket
            s3Service.putObject(
                    bucketName,
                    "event-images/%s/%s".formatted(eventId, eventImageName),
                    image
            );
        }


        //If Everything goes smoothly, return the event in SingleFullEventResponse
        return SingleFullEventResponse.builder()
                .fullEvent(getFullEventFromEvent(newEvent))
                .build();
    }

    public Response getFullEventById(String eventId) {

        //Use of private method getFullEventFromEventId() to generate FullEvent Object from eventId
        FullEvent fullEvent = getFullEventFromEventId(eventId);

        //Returns the event with id if successful
        return SingleFullEventResponse.builder()
                .fullEvent(fullEvent)
                .build();
    }

    public Response getDetailsEventById(String eventId) {
        //Use of private method getDetailsEventFromEventId() to generate DetailsEvent Object from eventId
        DetailsEvent detailsEvent = getDetailsEventFromEventId(eventId);

        String eventImageURL = "https://%s.s3.ap-southeast-1.amazonaws.com/event-images/%s/%s"
                .formatted(bucketName, eventId, detailsEvent.getEventImageName());
        detailsEvent.setEventImageURL(eventImageURL);
        // Returns the detailsEvent if successful
        return SingleDetailsEventResponse.builder()
                .detailsEvent(detailsEvent)
                .build();
    }

    public Response getAllExploreEvents() {
        // get the current day's date
        LocalDateTime today = LocalDateTime.now();

        // determine which events are after today
        List<ExploreEvent> events = returnExploreEventFormattedList(eventRepository.findByDatesGreaterThan(today));

        // input the imageURL
        for (ExploreEvent currentEvent : events) {
            // To get the URL for the eventImage
            String eventImageURL = "https://%s.s3.ap-southeast-1.amazonaws.com/event-images/%s/%s"
                    .formatted(bucketName, currentEvent.getEventId(), currentEvent.getEventImageName());
            currentEvent.setEventImageURL(eventImageURL);
        }

        // Returns the events with date after today if successful
        return ExploreEventsResponse.builder()
                .exploreEventList(events)
                .build();
    }

    // Helper Service Methods
    private List<LocalDateTime> convertArrToList(String[] arr) {
        List<LocalDateTime> list = new ArrayList<>();
        for (String s: arr) {
            LocalDateTime curr = LocalDateTime.parse(s, ISO_LOCAL_DATE_TIME);
            list.add(curr);
        }
        return list;
    }

    private FullEvent getFullEventFromEventId(String eventId) {
        //Finds event from repository, or else throw InvalidEventIdException()
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new InvalidEventIdException(eventId));

        //Find artist from repository, or else throw InvalidArtistIdException()
        Artist artist = artistRepository.findById(event.getArtistId())
                .orElseThrow(() -> new InvalidArtistIdException(event.getArtistId()));

        //Returns OutputEvent object from Event Object
        return event.returnFullEvent(artist);
    }


    private FullEvent getFullEventFromEvent(Event event) {

        //Find artist from repository, or else throw InvalidArtistIdException()
        Artist artist = artistRepository.findById(event.getArtistId())
                .orElseThrow(() -> new InvalidArtistIdException(event.getArtistId()));

        //Returns OutputEvent object from Event Object
        return event.returnFullEvent(artist);
    }

    private ExploreEvent getExploreEventFromEvent(Event event) {
        //Find artist from repository, or else throw InvalidArtistIdException()
        Artist artist = artistRepository.findById(event.getArtistId())
                .orElseThrow(() -> new InvalidArtistIdException(event.getArtistId()));

        //Returns OutputEvent object from Event Object
        return event.returnExploreEvent(artist.getName());

    }

    private DetailsEvent getDetailsEventFromEventId(String eventId) {
        //Finds event from repository, or else throw InvalidEventIdException()
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new InvalidEventIdException(eventId));

        Venue venue = venueRepository.findById(event.getVenue())
                .orElseThrow(() -> new InvalidVenueException());

        //Returns OutputEvent object from Event Object
        return event.returnDetailsEvent(venue);

    }

    private void eventRequestChecker(EventRequest request, Event oldEvent) {

        // Check 1: Check if artist exists in the first place in the ArtistRepository
        if (artistRepository.findById(request.getArtistId()).isEmpty()) {
            throw new InvalidArtistIdException(request.getArtistId());
        }

        /*
         * Check 2: Checks the request if there are other events that are created by the same artist and eventName
         *
         * Considers 2 scenarios to check for DuplicatedEventName:
         * 1. If addEvent(), the oldEvent is null
         * 2. If updateEvent(), the oldEvent will not be null and if there's a change in the eventName
         */
        if (oldEvent == null || !(oldEvent.getName().equals(request.getName()))) {

            //Checks Repository for the artistId and eventName
            if (eventRepository.findByArtistIdAndName(request.getArtistId(), request.getName()).isPresent()) {

                //If present, throw new DuplicatedEventException.
                throw new DuplicatedEventException(request.getArtistId(), request.getName());
            }
        }
    }

    private Event getEventClassFromRequest(EventRequest eventRequest, Event oldEvent) {
        //Checks if EventRequest isValid
        eventRequestChecker(eventRequest, oldEvent);

        //Create a ArrayList<LocalDateTime> from String[] dates
        List<LocalDateTime> datesList = convertArrToList(eventRequest.getDates());

        //Create a ArrayList<LocalDateTime> from String[] ticketSalesDate
        List<LocalDateTime> ticketSalesDateList = convertArrToList(eventRequest.getTicketSalesDate());

        //Build event
        Event event = Event.builder()
                .name(eventRequest.getName())
                .eventImageName(eventRequest.getEventImageName())
                .description(eventRequest.getDescription())
                .dates(datesList)
                .categories(Arrays.asList(eventRequest.getCategories()))
                .artistId(eventRequest.getArtistId())
                .seatingImagePlan(eventRequest.getSeatingImagePlan())
                .ticketSalesDate(ticketSalesDateList)
                .venue(eventRequest.getVenue())
                .build();

        //If updateEvent (oldEvent != null), set eventId to oldEvent eventId.
        if (oldEvent != null) { event.setId(oldEvent.getId()); }

        return event;
    }

    private List<ExploreEvent> returnExploreEventFormattedList(List<Event> eventList) {
        List<ExploreEvent> outList = new ArrayList<>();
        for (Event event : eventList) {
            //Use of private method getOutputEventFromEventId() to generate OutputEvent Object from eventId
            ExploreEvent exploreEvent = getExploreEventFromEvent(event);
            //Add outputEvent into outList
            outList.add(exploreEvent);
        }
        return outList;
    }

}
