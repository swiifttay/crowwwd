package com.cs203.g1t4.backend.service;

import com.cs203.g1t4.backend.data.request.event.AddEventRequest;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.models.exceptions.*;
import org.springframework.stereotype.Service;

import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.event.SingleEventResponse;
import com.cs203.g1t4.backend.models.Event;
import com.cs203.g1t4.backend.repository.EventRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    public Response addEvent(AddEventRequest request) {

        //If any missing fields (Exception of eventImageName and seatingImagePlan)
        if (request.getName() == null || request.getDescription() == null || request.getDates() == null ||
            request.getVenue() == null || request.getCategories() == null || request.getArtistId() == null ||
            request.getTicketSalesDate() == null) {
            throw new MissingFieldsException();
        }

        //Consideration: Check if artist exists in the first place in the ArtistRepository

        //Checks the request if there are other events that are created by the same artist and eventName
        //If so, throw new DuplicatedEventException.
        if (eventRepository.findByArtistIdAndName(request.getArtistId(), request.getName()).isPresent()) {
            throw new DuplicatedEventException(request.getArtistId(), request.getName());
        }

        //If all goes well, create the events class
        Event event = Event.builder()
                .name(request.getName())
                .eventImageName(request.getEventImageName())
                .description(request.getDescription())
                .dates(request.getDates())
                .venue(request.getVenue())
                .categories(request.getCategories())
                .artistId(request.getArtistId())
                .seatingImagePlan(request.getSeatingImagePlan())
                .ticketSalesDate(request.getTicketSalesDate())
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

    public Response findEventById(String eventId) {

        //Finds event from repository, or else throw InvalidEventIdException()
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new InvalidEventIdException(eventId));

        //Returns the event with id if successful
        return SingleEventResponse.builder()
                .event(event)
                .build();
    }
  
}
