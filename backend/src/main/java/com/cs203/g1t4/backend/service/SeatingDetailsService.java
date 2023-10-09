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

        //Checks if eventRepository contains an event of specific eventId
        if (eventRepository.findById(eventId).isEmpty()) { throw new InvalidEventIdException(eventId); }

        //Checks if there already exists a seatingDetails with the eventId
        if (seatingDetailsRepository.findEventSeatingDetailsByEventId(eventId).isPresent()) {
            throw new DuplicateSeatingDetailsException(eventId);
        }

        //Generates a seatingDetails for the specific event using the details from both the requestBody and the eventId.
        EventSeatingDetails seatingDetails = EventSeatingDetails.builder()
                                                .eventId(eventId)
                                                .categories(request.getCategories())
                                                .build();

        //Save the eventSeatingDetails into the repository.
        seatingDetailsRepository.save(seatingDetails);

        return SuccessResponse.builder()
                .response("Seating Details have been added successfully")
                .build();
    }

    public Response deleteSeatingDetails(String eventId) {

        //Find the EventSeatingDetails from the repository, else throws InvalidSeatingDetailsException()
        EventSeatingDetails seatingDetails = seatingDetailsRepository.findEventSeatingDetailsByEventId(eventId)
                .orElseThrow(() -> new InvalidSeatingDetailsException(eventId));

        //Deletion from repository if present
        seatingDetailsRepository.deleteById(eventId);

        return SeatingDetailsResponse.builder()
                .seatingDetails(seatingDetails)
                .build();
    }
    
    public Response updateSeatingDetails(String eventId, SeatingDetailsRequest request) {

        //Find EventId in EventSeatingDetails, else throws InvalidSeatingDetailsException(eventId)
        EventSeatingDetails seatingDetails = seatingDetailsRepository.findEventSeatingDetailsByEventId(eventId)
                .orElseThrow(() -> new InvalidSeatingDetailsException(eventId));

        //Set the category of the seatingDetails to the category in requestBody
        seatingDetails.setCategories(request.getCategories());

        //Save the updated seatingDetails into the repository
        seatingDetailsRepository.save(seatingDetails);

        return SeatingDetailsResponse.builder()
                .seatingDetails(seatingDetails)
                .build();
    }

    public Response getSeatingDetailsById(String eventId) {

        //Find EventId in EventSeatingDetails, else throws InvalidSeatingDetailsException(eventId)
        EventSeatingDetails seatingDetails = seatingDetailsRepository.findEventSeatingDetailsByEventId(eventId)
                .orElseThrow(() -> new InvalidSeatingDetailsException(eventId));

        return SeatingDetailsResponse.builder()
                .seatingDetails(seatingDetails)
                .build();
    }
}
