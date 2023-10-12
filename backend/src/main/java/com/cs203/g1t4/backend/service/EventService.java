package com.cs203.g1t4.backend.service;

import com.cs203.g1t4.backend.data.request.event.EventRequest;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.data.response.event.EventResponse;
import com.cs203.g1t4.backend.models.Artist;
import com.cs203.g1t4.backend.models.event.OutputEvent;
import com.cs203.g1t4.backend.models.exceptions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cs203.g1t4.backend.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.event.SingleEventResponse;
import com.cs203.g1t4.backend.models.event.Event;
import com.cs203.g1t4.backend.repository.EventRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final ArtistRepository artistRepository;
    private final S3Service s3Service;

    @Value("${aws.bucket.name}")
    private String bucketName;

    // Main Service Methods

    /**
     * Adds a new event to the repository.
     *
     * @param request a RegisterRequest object containing the new event info to be created
     * @return SuccessResponse "Event has been created successfully"
     */
    public Response addEvent(EventRequest request) {

        //Creates newEvent using private method getEventClassFromRequest(EventRequest request, Event oldEvent)
        Event event = getEventClassFromRequest(request, null);

        //Saves event into database
        eventRepository.save(event);

        //If Everything goes smoothly, SuccessResponse will be created
        return SuccessResponse.builder()
                .response("Event has been created successfully")
                .build();
    }

    /**
     * Deletes an event from the repository based on the eventId.
     *
     * @param eventId a String object containing the eventId of the event to be deleted.
     * @return a SingleEventResponse object containing the deleted Event Object in the form of an OutputEvent.
     */
    public Response deleteEventById(String eventId) {

        //Checks if there is an event with the specified eventID in the repository
        //If there are any exceptions, it will be propagated out
        OutputEvent outputEvent = getOutputEventFromEventId(eventId);

        //If event can be found, delete it from repository
        eventRepository.deleteById(eventId);

        //If Everything goes smoothly, return the event in SingleEventResponse
        return SingleEventResponse.builder()
                .outputEvent(outputEvent)
                .build();
    }

    /**
     * Updates an event from the repository based on the eventId and request.
     *
     * @param eventId a String object containing the eventId of the event to be updated.
     * @param request a EventRequest object containing the new event info to be updated
     * @return a SingleEventResponse object containing the updated Event Object in the form of an OutputEvent.
     */
    public Response updateEventById(String eventId, EventRequest request) {

        //Checks if there is an event with the specified eventID in the repository
        //If event cannot be found, throws new InvalidEventIdException if no such event found
        Event oldEvent = eventRepository.findById(eventId).orElseThrow(() -> new InvalidEventIdException(eventId));

        //Creates newEvent using private method getEventClassFromRequest(EventRequest request, Event oldEvent)
        Event newEvent = getEventClassFromRequest(request, oldEvent);

        eventRepository.save(newEvent);

        //If Everything goes smoothly, return the event in SingleEventResponse
        return SingleEventResponse.builder()
                .outputEvent(getOutputEventFromEventId(newEvent.getId()))
                .build();
    }

    /**
     * Finds an event from the repository based on the eventId.
     *
     * @param eventId a String object containing the eventId of the event to be found.
     * @return a SingleEventResponse object containing the found Event Object in the form of an OutputEvent.
     */
    public Response findEventById(String eventId) {

        //Use of private method getOutputEventFromEventId() to generate OutputEvent Object from eventId
        OutputEvent outputEvent = getOutputEventFromEventId(eventId);

        //Returns the event with id if successful
        return SingleEventResponse.builder()
                .outputEvent(outputEvent)
                .build();
    }

    /**
     * Finds a list of events from the repository that happens after today.
     *
     * @return a EventResponse object containing the List of OutputEvent objects.
     */
    public Response getAllEventsAfterToday() {
        // get today's date
        LocalDateTime today = LocalDateTime.now();

        // get all the events after today, or else returns an empty List<Event>
        List<OutputEvent> events = returnFormattedList(eventRepository.findByDatesGreaterThan(today));

        // Get the URL to the image of each event
        for (OutputEvent currentEvent : events) {
            // To get the URL for the eventImage
            String eventImageURL = "https://%s.s3.ap-southeast-1.amazonaws.com/event-images/%s/%s"
                    .formatted(bucketName, currentEvent.getEventId(), currentEvent.getEventImageName());
            currentEvent.setEventImageURL(eventImageURL);
        }

        // Returns the events with date after today if successful
        return EventResponse.builder()
            .events(events)
            .build();
    }

    /**
     * Finds a list of events from the repository that happens between a date range.
     *
     * @param beginDateRange a String object containing the start of the date range.
     * @param endDateRange a String object containing the end of the date range.
     * @return a EventResponse object containing the List of OutputEvent objects.
     */
    public Response getEventBetweenDateRange(String beginDateRange, String endDateRange) {
        
        // convert the start and end date to LocalDateTime class between 00:00 of start and 23:59 of end
        LocalDateTime beginDateRangeLDT = LocalDate.parse(beginDateRange).atStartOfDay();
        LocalDateTime endDateRangeLDT = LocalDate.parse(endDateRange).atTime(LocalTime.MAX);

        // get all the events between those two dates
        List<OutputEvent> events = returnFormattedList(eventRepository.findByDatesBetween(beginDateRangeLDT, endDateRangeLDT));

        // Get the URL to the image of each event
        for (OutputEvent currentEvent : events) {
            // To get the URL for the eventImage
            String eventImageURL = "https://%s.s3.ap-southeast-1.amazonaws.com/event-images/%s/%s"
                    .formatted(bucketName, currentEvent.getEventId(), currentEvent.getEventImageName());
            currentEvent.setEventImageURL(eventImageURL);
        }

        // Return the events with date after today if successful
        return EventResponse.builder()
                .events(events)
                .build();
    }

    /**
     * Uploads an image for an event.
     * If event cannot be found in repository based on eventId, throw InvalidEventIdException.
     *
     * @param eventId a String object containing the eventId of the event.
     * @param multipartFile a MultipartFile object containing the image.
     * @return a SuccessResponse containing "Image has been successfully uploaded".
     */
    public SuccessResponse uploadEventImage(String eventId,
                                            MultipartFile multipartFile) {
        // Get information on which event to edit from
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new InvalidEventIdException(eventId));

        // Get the event image name
        String eventImageName = event.getEventImageName();

        // Check if this is to update image of the event or to input a new one
        if (eventImageName == null) {
            eventImageName = multipartFile.getOriginalFilename();
        }

        // Put the image into the bucket
        s3Service.putObject(
                bucketName,
                "event-images/%s/%s".formatted(eventId, eventImageName),
                multipartFile
        );

        // Return
        return SuccessResponse.builder()
                .response("Image has been successfully uploaded")
                .build();
    }

    /**
     * Obtains the eventImageURL and returns as part of the message body of the SuccessResponse.
     *
     * @param eventId a String object containing the eventId to obtain the eventImageURL from.
     * @return a SuccessResponse object with message containing the eventImageURL.
     */
    public SuccessResponse getEventImage(String eventId) {
        String eventImageUrl = getEventImageUrl(eventId);

        // Return
        return SuccessResponse.builder()
                .response("Event Image URL: " + eventImageUrl)
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
        for (String s: arr) {
            LocalDateTime curr = LocalDateTime.parse(s, ISO_LOCAL_DATE_TIME);
            list.add(curr);
        }
        return list;
    }

    /**
     * Obtains an Event Object from String eventID and converts the Event Object to an OutputEvent Object
     * If event with inputted eventID cannot be found, throw InvalidEventIdException.
     * If artist with inputted artistID cannot be found, throw InvalidArtistIdException.
     *
     * @param eventId a String object containing the event ID of the event to be converted.
     * @return the OutputEvent object converted from the Event object.
     */
    private OutputEvent getOutputEventFromEventId(String eventId)
            throws InvalidArtistIdException, InvalidEventIdException{
        //Finds event from repository, or else throw InvalidEventIdException()
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new InvalidEventIdException(eventId));

        //Find artist from repository, or else throw InvalidArtistIdException()
        Artist artist = artistRepository.findById(event.getArtistId())
                .orElseThrow(() -> new InvalidArtistIdException(event.getArtistId()));

        //Returns OutputEvent object from Event Object
        return event.returnOutputEvent(artist);
    }

    /**
     * Checks if an EventRequest object is valid.
     * If artist with inputted artistID cannot be found, throw InvalidArtistIdException.
     * If event with the same artist and eventName already exists, throw DuplicatedEventException.
     *
     * @param request a EventRequest object containing the new event info to be created/updated
     * @param oldEvent an Event object containing the event info of the user that has to be updated. null for creation
     */
    private void eventRequestChecker(EventRequest request, Event oldEvent)
            throws DuplicatedEventException, InvalidArtistIdException {

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

    /**
     * Creates Event object from EventRequest object
     *
     * @param eventRequest a EventRequest object containing the new event info to be created/updated
     * @param oldEvent an Event object containing the event info of the user that has to be updated. null for creation
     * @return the Event object that has been created/updated
     */
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
                .build();

        //If updateEvent (oldEvent != null), set eventId to oldEvent eventId.
        if (oldEvent != null) { event.setId(oldEvent.getId()); }

        return event;
    }

    /**
     * Converts a List of Event objects to a List of a OutputEvent objects
     *
     * @param eventList a List of Event objects to be converted
     * @return the List of a OutputEvent objects converted from input
     */
    private List<OutputEvent> returnFormattedList(List<Event> eventList) {
        List<OutputEvent> outList = new ArrayList<>();
        for (Event event : eventList) {
            //Use of private method getOutputEventFromEventId() to generate OutputEvent Object from eventId
            OutputEvent outputEvent = getOutputEventFromEventId(event.getId());
            //Add outputEvent into outList
            outList.add(outputEvent);
        }
        return outList;
    }

    /**
     * Returns an eventImageUrl based on the Event object, which is stated by the eventId.
     *
     * @param eventId a String object containing the EventId of the event.
     * @return a String object containing the eventImageUrl.
     */
    public String getEventImageUrl(String eventId) throws InvalidEventIdException {
        // Get information on which event to edit from
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new InvalidEventIdException(eventId));

        String eventImageName = event.getEventImageName();
        // check if there is any Image to return
        if (eventImageName.isBlank()) {
            throw new InvalidEventIdException(eventId);
        }


        // To get the URL for the eventImage
        String eventImageURL = "https://%s.s3.ap-southeast-1.amazonaws.com/event-images/%s/%s"
                .formatted(bucketName, event.getId(), event.getEventImageName());

        // Implement catch error in the event no image is saved.

        // Return
        return eventImageURL;
    }

}
