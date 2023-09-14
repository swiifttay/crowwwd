package com.cs203.g1t4.backend.service;

import com.cs203.g1t4.backend.data.request.event.EventRequest;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.data.response.event.EventResponse;
import com.cs203.g1t4.backend.models.Artist;
import com.cs203.g1t4.backend.models.event.OutputEvent;
import com.cs203.g1t4.backend.models.exceptions.*;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.cs203.g1t4.backend.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.event.SingleEventResponse;
import com.cs203.g1t4.backend.models.event.Event;
import com.cs203.g1t4.backend.repository.EventRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final ArtistRepository artistRepository;
    private final S3Service s3Service;

    @Value("${env.AWS_BUCKET_NAME}")
    private String bucketName;

    public Response addEvent(EventRequest request) {

        //Checks if EventRequest isValid
        eventRequestChecker(request, null);

        //Create a ArrayList<LocalDateTime> from String[] dates
        List<LocalDateTime> datesList = convertArrToList(request.getDates());

        //Create a ArrayList<LocalDateTime> from String[] ticketSalesDate
        List<LocalDateTime> ticketSalesDateList = convertArrToList(request.getTicketSalesDate());

        //If all goes well, create the event object
        Event event = Event.builder()
                .name(request.getName())
                .eventImageName(request.getEventImageName())
                .description(request.getDescription())
                .dates(datesList)
                .venue(request.getVenue())
                .categories(Arrays.asList(request.getCategories()))
                .artistId(request.getArtistId())
                .seatingImagePlan(request.getSeatingImagePlan())
                .ticketSalesDate(ticketSalesDateList)
                .build();

        eventRepository.save(event);

        //If Everything goes smoothly, SuccessResponse will be created
        return SuccessResponse.builder()
                .response("Event has been created successfully")
                .build();
    }

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

    public Response updateEventById(String eventId, EventRequest request) {

        //Checks if there is an event with the specified eventID in the repository
        //If event cannot be found, throws new InvalidEventIdException if no such event found
        Event oldEvent = eventRepository.findById(eventId).orElseThrow(() -> new InvalidEventIdException(eventId));

        //Checks if EventRequest isValid
        eventRequestChecker(request, oldEvent);

        //Create a ArrayList<LocalDateTime> from String[] dates
        List<LocalDateTime> datesList = convertArrToList(request.getDates());

        //Create a ArrayList<LocalDateTime> from String[] ticketSalesDate
        List<LocalDateTime> ticketSalesDateList = convertArrToList(request.getTicketSalesDate());

        //If event can be found, delete it from repository
        Event newEvent = Event.builder()
                .id(oldEvent.getId())
                .name(request.getName())
                .eventImageName(request.getEventImageName())
                .description(request.getDescription())
                .dates(datesList)
                .categories(Arrays.asList(request.getCategories()))
                .artistId(request.getArtistId())
                .seatingImagePlan(request.getSeatingImagePlan())
                .ticketSalesDate(ticketSalesDateList)
                .build();

        eventRepository.save(newEvent);

        //If Everything goes smoothly, return the event in SingleEventResponse
        return SingleEventResponse.builder()
                .outputEvent(getOutputEventFromEventId(newEvent.getId()))
                .build();
    }

    public Response findEventById(String eventId) {

        //Use of private method getOutputEventFromEventId() to generate OutputEvent Object from eventId
        OutputEvent outputEvent = getOutputEventFromEventId(eventId);

        //Returns the event with id if successful
        return SingleEventResponse.builder()
                .outputEvent(outputEvent)
                .build();
    }

    public Response getAllEventsAfterToday() {
        // get today's date
        LocalDateTime today = LocalDateTime.now();

        // get all the events after today, or else returns an empty List<Event>
        List<OutputEvent> events = returnFormattedList(eventRepository.findByDatesGreaterThan(today));

        // Returns the events with date after today if successful
        return EventResponse.builder()
            .events(events)
            .build();
    }

    public Response getEventBetweenDateRange(String beginDateRange, String endDateRange) {
        
        // convert the start and end date to LocalDateTime class between 00:00 of start and 23:59 of end
        LocalDateTime beginDateRangeLDT = LocalDate.parse(beginDateRange).atStartOfDay();
        LocalDateTime endDateRangeLDT = LocalDate.parse(endDateRange).atTime(LocalTime.MAX);

        // get all the events between those two dates
        List<OutputEvent> events = returnFormattedList(eventRepository.findByDatesBetween(beginDateRangeLDT, endDateRangeLDT));

        // Return the events with date after today if successful
        return EventResponse.builder()
                .events(events)
                .build();
    }

    private void eventRequestChecker(EventRequest request, Event oldEvent) {

        // Check 1: Check if artist exists in the first place in the ArtistRepository
        if (artistRepository.findById(request.getArtistId()).isEmpty()) {
            throw new InvalidArtistIdException(request.getArtistId());
        }

        /*
         * Check 2  Checks the request if there are other events that are created by the same artist and eventName
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

    private List<LocalDateTime> convertArrToList(String[] arr) {
        List<LocalDateTime> list = new ArrayList<>();
        for (String s: arr) {
            LocalDateTime curr = LocalDateTime.parse(s, ISO_LOCAL_DATE_TIME);
            list.add(curr);
        }
        return list;
    }

    private OutputEvent getOutputEventFromEventId(String eventId) {
        //Finds event from repository, or else throw InvalidEventIdException()
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new InvalidEventIdException(eventId));

        //Find artist from repository, or else throw InvalidArtistIdException()
        Artist artist = artistRepository.findById(event.getArtistId())
                .orElseThrow(() -> new InvalidArtistIdException(event.getArtistId()));

        //Returns OutputEvent object from Event Object
        return event.returnOutputEvent(artist);
    }

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

    public SuccessResponse uploadEventImage(String eventId,
                                           MultipartFile multipartFile) {
        // Get information on which event to edit from
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new InvalidEventIdException(eventId));

        // Get the event image name
        String eventImageId;

        // Check if this is to update image of the event or to input a new one
        if (event.getEventImageName() == null) {
            eventImageId = multipartFile.getOriginalFilename();
        } else {
            eventImageId = event.getEventImageName();
        }

        // Put the image into the bucket
        s3Service.putObject(
                bucketName,
                "event-images/%s/%s".formatted(eventId, eventImageId),
                multipartFile
        );

        //Update information on the Event Image Name if it changed previously
        Event newEvent = Event.builder()
                .id(event.getId())
                .name(event.getName())
                .eventImageName(eventImageId)
                .description(event.getDescription())
                .dates(event.getDates())
                .categories(event.getCategories())
                .artistId(event.getArtistId())
                .seatingImagePlan(event.getSeatingImagePlan())
                .ticketSalesDate(event.getTicketSalesDate())
                .build();

        // Save to repository
        eventRepository.save(newEvent);

        // Return the success response if the image was saved successfully
        return SuccessResponse.builder()
                .response("Event image has been uploaded successfully")
                .build();
    }


//    TODO: Create a getEventImageLink to add to the response event
//    public byte[] getEventImage(String eventId) {
//        var event = eventRepository.findById(eventId)
//                .orElseThrow(() -> new InvalidEventIdException(eventId));
//
////        if (event.getEventImageName().isBlank()) {
////            throw new ResourceNotFoundException(
////                    "customer with id [%s] profile image not found".formatted(customerId));
////        }
//
//        byte[] eventImage = s3Service.getObject(
//                bucketName,
//                "profile-images/%s/%s".formatted(eventId, event.getEventImageName())
//        );
//        return eventImage;
//    }

}
