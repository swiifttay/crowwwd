package com.cs203.g1t4.backend.service;

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

        //Finds user from repository, or else throw Invalid token exception
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new InvalidTokenException());

        //Returns the event with id if successful
        return SingleEventResponse.builder()
                .event(event)
                .build();
    }
  
}
