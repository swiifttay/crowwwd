package com.cs203.g1t4.backend.service;

import com.cs203.g1t4.backend.data.request.event.SeatingDetailsRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.data.response.event.SeatingDetailsResponse;
import com.cs203.g1t4.backend.models.Category;
import com.cs203.g1t4.backend.models.event.Event;
import com.cs203.g1t4.backend.models.event.EventSeatingDetails;
import com.cs203.g1t4.backend.models.exceptions.DuplicateSeatingDetailsException;
import com.cs203.g1t4.backend.models.exceptions.InvalidEventIdException;
import com.cs203.g1t4.backend.models.exceptions.InvalidSeatingDetailsException;
import com.cs203.g1t4.backend.repository.EventRepository;
import com.cs203.g1t4.backend.repository.SeatingDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SeatingDetailsService {
    private final EventRepository eventRepository;
    private final SeatingDetailsRepository seatingDetailsRepository;

    public SuccessResponse addSeatingDetails(String eventId, SeatingDetailsRequest request) {

        String requestId = request.getEventId();

        Event event = eventRepository.findById(eventId).orElseThrow(() -> new InvalidEventIdException(eventId));

        Optional<EventSeatingDetails> duplicateSeatingDetails = seatingDetailsRepository.findEventSeatingDetailsByEventId(requestId);

        if (duplicateSeatingDetails.isPresent()) {
            throw new DuplicateSeatingDetailsException(requestId);
        }

        EventSeatingDetails seatingDetails = EventSeatingDetails.builder()
                                                .eventId(eventId)
                                                .categories(request.getCategories())
                                                .build();

        seatingDetailsRepository.save(seatingDetails);

        return SuccessResponse.builder()
                .response("Seating Details have been added successfully")
                .build();
    }

    public Response deleteSeatingDetails(String eventId) {

        EventSeatingDetails seatingDetails = seatingDetailsRepository.findEventSeatingDetailsByEventId(eventId)
                .orElseThrow(() -> new InvalidSeatingDetailsException(eventId));

        seatingDetailsRepository.deleteById(eventId);

        return SeatingDetailsResponse.builder()
                .seatingDetails(seatingDetails)
                .build();
    }
    
    public Response updateSeatingDetails(String eventId, SeatingDetailsRequest request) {

        String requestId = request.getEventId();

        EventSeatingDetails seatingDetails = seatingDetailsRepository.findEventSeatingDetailsByEventId(eventId)
                .orElseThrow(() -> new InvalidSeatingDetailsException(eventId));

        if (requestId.equals(eventId)) {
            seatingDetails.setCategories(request.getCategories());
        }

        seatingDetailsRepository.save(seatingDetails);

        return SeatingDetailsResponse.builder()
                .seatingDetails(seatingDetails)
                .build();
    }

    public Response getSeatingDetailsById(String eventId) {

        EventSeatingDetails seatingDetails = seatingDetailsRepository.findEventSeatingDetailsByEventId(eventId)
                .orElseThrow(() -> new InvalidSeatingDetailsException(eventId));

        return SeatingDetailsResponse.builder()
                .seatingDetails(seatingDetails)
                .build();
    }
}
