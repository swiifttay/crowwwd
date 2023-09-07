package com.cs203.g1t4.backend.service;

import com.cs203.g1t4.backend.models.exceptions.InvalidEventIdException;
import org.springframework.stereotype.Service;

import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.event.SingleEventResponse;
import com.cs203.g1t4.backend.models.Event;
import com.cs203.g1t4.backend.models.exceptions.InvalidTokenException;
import com.cs203.g1t4.backend.repository.EventRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

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
