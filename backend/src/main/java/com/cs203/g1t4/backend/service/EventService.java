package com.cs203.g1t4.backend.service;

import com.cs203.g1t4.backend.data.request.event.EventRequest;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.data.response.event.EventResponse;
import com.cs203.g1t4.backend.models.exceptions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.event.SingleEventResponse;
import com.cs203.g1t4.backend.models.Event;
import com.cs203.g1t4.backend.repository.EventRepository;

import lombok.RequiredArgsConstructor;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

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
        //If event cannot be found, throws new InvalidEventIdException if no such event found
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new InvalidEventIdException(eventId));

        //If event can be found, delete it from repository
        eventRepository.deleteById(eventId);

        //If Everything goes smoothly, return the event in SingleEventResponse
        return SingleEventResponse.builder()
                .event(event)
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
                .event(newEvent)
                .build();
    }

    public List<LocalDateTime> convertArrToList(String[] arr) {
        List<LocalDateTime> list = new ArrayList<>();
        for (String s: arr) {
            LocalDateTime curr = LocalDateTime.parse(s, ISO_LOCAL_DATE_TIME);
            list.add(curr);
        }
        return list;
    }

    public void eventRequestChecker(EventRequest request, Event oldEvent) {

        // Consideration: Check if artist exists in the first place in the ArtistRepository

        /*
         * Check: Checks the request if there are other events that are created by the same artist and eventName
         *
         * Considers 2 possibilities to check for DuplicatedEventName:
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

    public Response findEventById(String eventId) {

        //Finds event from repository, or else throw InvalidEventIdException()
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new InvalidEventIdException(eventId));

        //Returns the event with id if successful
        return SingleEventResponse.builder()
                .event(event)
                .build();
    }


    public Response getAllEventsAfterToday() {
        // get today's date
        LocalDateTime today = LocalDateTime.now();

        // get all the events after today, or else throw No Event exception
        List<Event> event = eventRepository.findByDatesGreaterThan(today)
            .orElseThrow(() -> new NoEventException());

        // Returns the events with date after today if successful
        return EventResponse.builder()
            .events(event)
            .build();
    }

    public Response getEventBetweenDateRange(String beginDateRange, String endDateRange) {
        
        // convert the start and end date to LocalDateTime class between 00:00 of start and 23:59 of end
        LocalDateTime beginDateRangeLDT = LocalDate.parse(beginDateRange).atStartOfDay();
        LocalDateTime endDateRangeLDT = LocalDate.parse(endDateRange).atTime(LocalTime.MAX);

        // get all the events between those two dates
        List<Event> event = eventRepository.findByDatesBetween(beginDateRangeLDT, endDateRangeLDT)
                .orElseThrow(() -> new NoEventException());

        // Return the events with date after today if successful
        return EventResponse.builder()
                .events(event)
                .build();
    }

}
