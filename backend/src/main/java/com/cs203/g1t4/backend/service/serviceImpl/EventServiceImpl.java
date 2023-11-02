package com.cs203.g1t4.backend.service.serviceImpl;

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
import com.cs203.g1t4.backend.service.services.EventService;
import com.cs203.g1t4.backend.service.services.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final ArtistRepository artistRepository;
    private final VenueRepository venueRepository;
    private final S3Service s3Service;

    @Value("${aws.bucket.name}")
    private String bucketName;

    /**
     * Adds a new event to the repository.
     *
     * @param request a EventRequest object containing the new event info to be created
     * @param image a MultipartFile object containing the image corresponding to the event
     * @return SuccessResponse "Event has been created successfully"
     */
    // Main Service Methods
    public Response addFullEvent(EventRequest request, MultipartFile image) {

        // Creates newEvent using private method
        // getEventClassFromRequest(EventRequest request, Event oldEvent)
        Event event = getEventClassFromRequest(request, null);

        // Saves event into database
        eventRepository.save(event);

        // Get the event image name
        String eventImageName = image.getOriginalFilename();

        // Put the image into the bucket
        s3Service.putObject(
                bucketName,
                "event-images/%s/%s".formatted(event.getId(), eventImageName),
                image
        );

        //Checks if venue exists
        Venue venue = venueRepository.findById(request.getVenue()).orElseThrow(() -> new InvalidVenueException());

        //If Everything goes smoothly, SuccessResponse will be created
        return SuccessResponse.builder()
                .response("Event has been created successfully")
                .build();
    }

    /**
     * Deletes an event from the repository based on the eventId.
     *
     * @param eventId a String object containing the eventId of the event to be deleted.
     * @return a SingleFullEventResponse object containing the deleted Event Object in the form of a FullEvent.
     */
    public Response deleteFullEventById(String eventId) {

        //Checks if there is an event with the specified eventID in the repository
        //If there are any exceptions, it will be propagated out
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new InvalidEventIdException(eventId));

        // If event can be found, delete it from repository
        eventRepository.deleteById(eventId);

        //If Everything goes smoothly, return the event in SingleFullEventResponse
        return SingleFullEventResponse.builder()
                .fullEvent(getFullEventFromEvent(event))
                .build();
    }

    /**
     * Updates an event from the repository based on the eventId, request and image.
     *
     * @param eventId a String object containing the eventId of the event to be updated.
     * @param request a EventRequest object containing the new event info to be updated
     * @param image a MultipartFile object containing the new image to be updated
     * @return a SingleFullEventResponse object containing the updated Event Object in the form of a FullEvent.
     */
    public Response updateFullEventById(String eventId, EventRequest request, MultipartFile image) {

        // Checks if there is an event with the specified eventID in the repository
        // If event cannot be found, throws new InvalidEventIdException if no such event
        // found
        Event oldEvent = eventRepository.findById(eventId).orElseThrow(() -> new InvalidEventIdException(eventId));

        // Creates newEvent using private method getEventClassFromRequest(EventRequest
        // request, Event oldEvent)
        Event newEvent = getEventClassFromRequest(request, oldEvent);

        eventRepository.save(newEvent);

        if (image != null && !image.isEmpty()) {
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

    /**
     * Finds an ExploreEvent from the repository based on the eventId.
     *
     * @param eventId a String object containing the eventId of the event to be found.
     * @return a SingleFullEventResponse object containing the found Event Object in the form of an ExploreEvent.
     */
    public Response getFullEventById(String eventId) {

        //Use of private method getFullEventFromEventId() to generate FullEvent Object from eventId
        FullEvent fullEvent = getFullEventFromEventId(eventId);

        //Returns the event with id if successful
        return SingleFullEventResponse.builder()
                .fullEvent(fullEvent)
                .build();
    }

    /**
     * Finds a DetailsEvent from the repository based on the eventId.
     *
     * @param eventId a String object containing the eventId of the event to be found.
     * @return a SingleDetailsEventResponse object containing the found Event Object in the form of an DetailsEvent.
     */
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

    /**
     * Finds a list of ExploreEvent from the repository that happens after today.
     *
     * @return a ExploreEventsResponse object containing the List of ExploreEvent objects.
     */
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
    /**
     * Converts a String[] into a list of LocalDateTime. Dates are stored in the String[] in the forms of
     * "<yyyy>-<MM>-<dd>T<HH>:<mm>:<ss>", example: 2011-12-03T10:15:30
     *
     * @param arr a String[] array containing dates
     * @return a List of LocalDateTime objects converted from an array of String objects.
     */
    private List<LocalDateTime> convertArrToList(String[] arr) {
        List<LocalDateTime> list = new ArrayList<>();
        for (String s : arr) {
            LocalDateTime curr = LocalDateTime.parse(s, ISO_LOCAL_DATE_TIME);
            list.add(curr);
        }
        return list;
    }

    /**
     * Obtains an Event Object from String eventID and converts the Event Object to a FullEvent Object
     * If event with inputted eventID cannot be found, throw InvalidEventIdException.
     * If artist with inputted artistID cannot be found, throw InvalidArtistIdException.
     *
     * @param eventId a String object containing the event ID of the event to be converted.
     * @return the FullEvent object converted from the Event object.
     */
    private FullEvent getFullEventFromEventId(String eventId) throws InvalidEventIdException, InvalidArtistIdException {
        //Finds event from repository, or else throw InvalidEventIdException()
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new InvalidEventIdException(eventId));

        // Find artist from repository, or else throw InvalidArtistIdException()
        Artist artist = artistRepository.findById(event.getArtistId())
                .orElseThrow(() -> new InvalidArtistIdException(event.getArtistId()));

        // Find venue from repository, or else throw InvalidVenueException()
        Venue venue = venueRepository.findById(event.getVenue())
                .orElseThrow(() -> new InvalidVenueException());

        //Returns OutputEvent object from Event Object
        return event.returnFullEvent(artist, venue);
    }

    /**
     * Obtains an Event Object from String eventID and converts the Event Object to a FullEvent Object
     * If artist with inputted artistID cannot be found, throw InvalidArtistIdException.
     *
     * @param event a Event object to be converted.
     * @return the FullEvent object converted from the Event object.
     */
    private FullEvent getFullEventFromEvent(Event event) throws InvalidArtistIdException{

        //Find artist from repository, or else throw InvalidArtistIdException()
        Artist artist = artistRepository.findById(event.getArtistId())
                .orElseThrow(() -> new InvalidArtistIdException(event.getArtistId()));

        // Find venue from repository, or else throw InvalidVenueException()
        Venue venue = venueRepository.findById(event.getVenue())
                .orElseThrow(() -> new InvalidVenueException());

        //Returns OutputEvent object from Event Object
        return event.returnFullEvent(artist, venue);
    }

    /**
     * Obtains an Event Object from String eventID and converts the Event Object to a ExploreEvent Object
     * If artist with inputted artistID cannot be found, throw InvalidArtistIdException.
     *
     * @param event a Event object to be converted.
     * @return the ExploreEvent object converted from the Event object.
     */
    private ExploreEvent getExploreEventFromEvent(Event event) throws InvalidArtistIdException{
        //Find artist from repository, or else throw InvalidArtistIdException()
        Artist artist = artistRepository.findById(event.getArtistId())
                .orElseThrow(() -> new InvalidArtistIdException(event.getArtistId()));

        //Returns OutputEvent object from Event Object
        return event.returnExploreEvent(artist.getName());

    }

    /**
     * Obtains an Event Object from String eventID and converts the Event Object to a DetailsEvent Object
     * If artist with inputted artistID cannot be found, throw InvalidArtistIdException.
     *
     * @param eventId a Event object to be converted.
     * @return the DetailsEvent object converted from the Event object.
     */
    private DetailsEvent getDetailsEventFromEventId(String eventId) throws InvalidEventIdException, InvalidVenueException{
        //Finds event from repository, or else throw InvalidEventIdException()
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new InvalidEventIdException(eventId));

        Venue venue = venueRepository.findById(event.getVenue())
                .orElseThrow(() -> new InvalidVenueException());

        //Returns OutputEvent object from Event Object
        return event.returnDetailsEvent(venue);

    }

    /**
     * Checks if an EventRequest object is valid.
     * If artist with inputted artistID cannot be found, throw InvalidArtistIdException.
     * If event with the same artist and eventName already exists, throw DuplicatedEventException.
     *
     * @param request a EventRequest object containing the new event info to be created/updated
     * @param oldEvent an Event object containing the event info of the user that has to be updated. null for creation
     */
    private void eventRequestChecker(EventRequest request, Event oldEvent) throws DuplicatedEventException, InvalidArtistIdException, InvalidVenueException {

        // Check 1: Check if artist exists in the first place in the ArtistRepository
        if (artistRepository.findById(request.getArtistId()).isEmpty()) {
            throw new InvalidArtistIdException(request.getArtistId());
        }

        // Check 2: Check if venue exists in the first place in the VenueRepository
        if (venueRepository.findById(request.getVenue()).isEmpty()) {
            throw new InvalidVenueException();
        }

        /*
         * Check 3: Checks the request if there are other events that are created by the same artist and eventName
         *
         * Considers 2 scenarios to check for DuplicatedEventName:
         * 1. If addEvent(), the oldEvent is null
         * 2. If updateEvent(), the oldEvent will not be null and if there's a change in
         * the eventName
         */
        if (oldEvent == null || !(oldEvent.getName().equals(request.getName()))) {

            // Checks Repository for the artistId and eventName
            if (eventRepository.findByArtistIdAndName(request.getArtistId(), request.getName()).isPresent()) {

                // If present, throw new DuplicatedEventException.
                throw new DuplicatedEventException(request.getArtistId(), request.getName());
            }
        }
    }

    /**
     * Creates Event object from EventRequest object
     *
     * @param eventRequest a EventRequest object containing the new event info to be created/updated
     * @param oldEvent an Event object containing the event info of the user that has to be updated. null for creation
     * @return the Event object that has been created/updated
     */
    private Event getEventClassFromRequest(EventRequest eventRequest, Event oldEvent) throws InvalidVenueException {
        // Checks if EventRequest isValid
        eventRequestChecker(eventRequest, oldEvent);

        // Create a ArrayList<LocalDateTime> from String[] dates
        List<LocalDateTime> datesList = convertArrToList(eventRequest.getDates());

        // Create a ArrayList<LocalDateTime> from String[] ticketSalesDate
        List<LocalDateTime> ticketSalesDateList = convertArrToList(eventRequest.getTicketSalesDate());

        //Checks if venue exists in the repository
        venueRepository.findById(eventRequest.getVenue()).orElseThrow(() -> new InvalidVenueException());

        //Retrieve Artist from database
        Artist artist = artistRepository.findById(eventRequest.getArtistId())
                .orElseThrow(() -> new InvalidArtistIdException(eventRequest.getArtistId()));

        //Create eventName without punctuation and spaces
        String aliasEventName = returnStringWithoutPunctuation(eventRequest.getName());

        //Create artistName without punctuation and spaces
        String aliasArtistName = returnStringWithoutPunctuation(artist.getName());

        // Build event
        Event event = Event.builder()
                .name(eventRequest.getName())
                .alias(ticketSalesDateList.get(0).getYear() + "_" + aliasEventName + "_" + aliasArtistName)
                .eventImageName(eventRequest.getEventImageName())
                .description(eventRequest.getDescription())
                .dates(datesList)
                .categories(Arrays.asList(eventRequest.getCategories()))
                .artistId(eventRequest.getArtistId())
                .seatingImagePlan(eventRequest.getSeatingImagePlan())
                .ticketSalesDate(ticketSalesDateList)
                .venue(eventRequest.getVenue())
                .build();

        // If updateEvent (oldEvent != null), set eventId to oldEvent eventId.
        if (oldEvent != null) {
            event.setId(oldEvent.getId());
        }

        return event;
    }

    private String returnStringWithoutPunctuation(String s) {
        StringBuilder stringWithoutPunctuation = new StringBuilder("");
        for (int i = 0 ; i < s.length() ; i++) {
            if (Character.isLetter(s.charAt(i))) {
                stringWithoutPunctuation.append(s.charAt(i));
            }
        }
        return stringWithoutPunctuation.toString();
    }

    /**
     * Converts a List of Event objects to a List of a ExploreEvent objects
     *
     * @param eventList a List of Event objects to be converted
     * @return the List of a ExploreEvent objects converted from input
     */
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
