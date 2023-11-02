package com.cs203.g1t4.backend.service.serviceImpl;

import com.cs203.g1t4.backend.data.request.event.SeatingDetailsRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.data.response.event.SeatingDetailsResponse;
import com.cs203.g1t4.backend.models.Category;
import com.cs203.g1t4.backend.models.event.EventSeatingDetails;
import com.cs203.g1t4.backend.models.exceptions.DuplicateSeatingDetailsException;
import com.cs203.g1t4.backend.models.exceptions.InvalidCategoryException;
import com.cs203.g1t4.backend.models.exceptions.InvalidEventIdException;
import com.cs203.g1t4.backend.models.exceptions.InvalidSeatingDetailsException;
import com.cs203.g1t4.backend.repository.EventRepository;
import com.cs203.g1t4.backend.repository.SeatingDetailsRepository;
import com.cs203.g1t4.backend.service.services.SeatingDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatingDetailsServiceImpl implements SeatingDetailsService {

    private final EventRepository eventRepository;
    private final SeatingDetailsRepository seatingDetailsRepository;
    private static final int NUM_SEATS_PER_ROW = 4;
    private static final int NUM_ROWS = 4;

    public SuccessResponse addSeatingDetails(String eventId, SeatingDetailsRequest request) {

        //Generates a seatingDetails for the specific event using the details from both the requestBody and the eventId.
        EventSeatingDetails seatingDetails = getEventSeatingDetailsFromRequest(eventId, request, false);

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

        //Generates a seatingDetails for the specific event using the details from both the requestBody and the eventId
        EventSeatingDetails seatingDetails = getEventSeatingDetailsFromRequest(eventId, request, false);

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

    //Public Helper methods
    public Response updateSeatingDetails(String eventId, String category, String seatsInformationString) {
        //Find EventId in EventSeatingDetails, else throws InvalidSeatingDetailsException(eventId)
        EventSeatingDetails eventSeatingDetails = seatingDetailsRepository.findEventSeatingDetailsByEventId(eventId)
                .orElseThrow(() -> new InvalidSeatingDetailsException(eventId));

        //Find listOfCategories in eventSeatingDetails
        List<Category> listOfCategory = eventSeatingDetails.getCategories();

        //Instantiate found with false to assume that the category is not found.
        boolean found = false;

        //Loops through each category in the listOfCategory
        for (int index = 0 ; index < listOfCategory.size() && !found; index++) {

            Category c = listOfCategory.get(index);

            if (c.getCategory().equals(category)) {
                found = true;
                c.setSeatsInformationString(seatsInformationString);
            }
        }

        if (!found) { throw new InvalidCategoryException(category); }

        //Save the updated seatingDetails into the repository
        seatingDetailsRepository.save(eventSeatingDetails);

        return SeatingDetailsResponse.builder()
                .seatingDetails(eventSeatingDetails)
                .build();
    }

    //Private Helper methods
    private EventSeatingDetails getEventSeatingDetailsFromRequest(String eventId, SeatingDetailsRequest request, boolean isUpdate)
            throws InvalidEventIdException, InvalidSeatingDetailsException, DuplicateSeatingDetailsException {

        //Checks if Event ID can be found in eventRepository
        if (eventRepository.findById(eventId).isEmpty()) {
            throw new InvalidEventIdException(eventId);
        }

        //SeatingDetailsRepository should not contain an event of specific eventId for new EventSeatingDetails AND
        //SeatingDetailsRepository should contain an event of specific eventId for updating EventSeatingDetails
        if (!isUpdate && seatingDetailsRepository.findById(eventId).isPresent()) {
            throw new DuplicateSeatingDetailsException(eventId);
        } else if ((isUpdate && seatingDetailsRepository.findById(eventId).isEmpty())) {
            throw new InvalidSeatingDetailsException(eventId);
        }

        //Checks if there already exists a seatingDetails with the eventId
        if (seatingDetailsRepository.findEventSeatingDetailsByEventId(eventId).isPresent()) {
            throw new DuplicateSeatingDetailsException(eventId);
        }

        //Calculate the total number of seats for each category based on default value
        int totalSeats = NUM_SEATS_PER_ROW * NUM_ROWS;

        //Creates the default String containing total number of seats with '0'
        StringBuilder stringBuilder = new StringBuilder(totalSeats);
        for (int i = 0 ; i < totalSeats ; i++) {
            stringBuilder.append('0');
        }

        //Loops through each Category in the request and add in the seatInformationString for each
        List<Category> listOfCategory = request.getCategories();
        for (int i = 0 ; i < listOfCategory.size() ; i++) {

            //Obtain each Category object
            Category category = listOfCategory.get(i);

            //Assign the String to the category object
            category.setSeatsInformationString(stringBuilder.toString());

            //Update the category in the list
            listOfCategory.set(i, category);
        }

        //Generates a seatingDetails for the specific event using the details from both the requestBody and the eventId.
        EventSeatingDetails seatingDetails = EventSeatingDetails.builder()
                .eventId(eventId)
                .categories(request.getCategories())
                .build();
        return seatingDetails;
    }
}
