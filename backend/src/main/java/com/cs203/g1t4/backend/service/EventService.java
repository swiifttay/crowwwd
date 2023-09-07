package com.cs203.g1t4.backend.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.event.EventResponse;
import com.cs203.g1t4.backend.data.response.event.SingleEventResponse;
import com.cs203.g1t4.backend.models.Event;
import com.cs203.g1t4.backend.models.exceptions.NoEventException;
import com.cs203.g1t4.backend.repository.EventRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {
  private final EventRepository eventRepository;

  public Response findEventById(String eventId) {

    // Finds event from repository, or else throw No Event exception
    Event event = eventRepository.findById(eventId)
        .orElseThrow(() -> new NoEventException("Event ID: " + eventId));

    // Returns the event with id if successful
    return SingleEventResponse.builder()
        .event(event)
        .build();
  }

  public Response getAllEventsAfterToday() {
    // get today's date
    LocalDateTime today = LocalDateTime.now();

    // get all the events after today, or else throw No Event exception
    List<Event> event = eventRepository.findAllAfterDate(today)
        .orElseThrow(() -> new NoEventException());

    // Returns the events with date after today if successful
    return EventResponse.builder()
        .events(event)
        .build();

  }

}
