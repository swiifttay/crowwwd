package com.cs203.g1t4.backend.service.serviceImpl;

import com.cs203.g1t4.backend.data.request.event.SeatingDetailsRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.data.response.event.SeatingDetailsResponse;
import com.cs203.g1t4.backend.models.Category;
import com.cs203.g1t4.backend.models.event.Event;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SeatingDetailsServiceImpl implements SeatingDetailsService {

    private final EventRepository eventRepository;
    private final SeatingDetailsRepository seatingDetailsRepository;
    private static final int NUM_SEATS_PER_ROW = 4;
    private static final int NUM_ROWS = 4;

    /**
     * Add Seating Details
     *
     * @param eventId a String object containing the eventId of the event
     * @param request a SeatingDetailsRequest object holding new SeatingDetails information
     * @return a SuccessResponse object containing "Seating Details have been added successfully"
     */
    public SuccessResponse addSeatingDetails(String eventId, SeatingDetailsRequest request) {

        //Generates a seatingDetails for the specific event using the details from both the requestBody and the eventId.
        EventSeatingDetails seatingDetails = getEventSeatingDetailsFromRequest(eventId, request, false);

        //Save the eventSeatingDetails into the repository.
        seatingDetailsRepository.save(seatingDetails);

        return SuccessResponse.builder()
                .response("Seating Details have been added successfully")
                .build();
    }

    /**
     * Delete Seating Details
     *
     * @param eventId a String object containing the eventId of the event
     * @return a SeatingDetailsResponse object containing a seatingDetails object
     */
    public Response deleteSeatingDetails(String eventId) throws InvalidSeatingDetailsException {

        //Find the EventSeatingDetails from the repository, else throws InvalidSeatingDetailsException()
        EventSeatingDetails seatingDetails = seatingDetailsRepository.findEventSeatingDetailsByEventId(eventId)
                .orElseThrow(() -> new InvalidSeatingDetailsException(eventId));

        //Deletion from repository if present
        seatingDetailsRepository.deleteById(eventId);

        return SeatingDetailsResponse.builder()
                .seatingDetails(seatingDetails)
                .build();
    }

    /**
     * Update Seating Details
     *
     * @param eventId a String object containing the eventId of the event
     * @param request a SeatingDetailsRequest object holding updated SeatingDetails information
     * @return a SeatingDetailsResponse object containing a seatingDetails object
     */
    public Response updateSeatingDetails(String eventId, SeatingDetailsRequest request) {

        //Generates a seatingDetails for the specific event using the details from both the requestBody and the eventId
        EventSeatingDetails seatingDetails = getEventSeatingDetailsFromRequest(eventId, request, false);

        //Save the updated seatingDetails into the repository
        seatingDetailsRepository.save(seatingDetails);

        return SeatingDetailsResponse.builder()
                .seatingDetails(seatingDetails)
                .build();
    }

    /**
     * Get Seating Details By eventId
     *
     * @param eventId a String object containing the eventId of the event
     * @return a SeatingDetailsResponse object containing a seatingDetails object
     */
    public Response getSeatingDetailsById(String eventId) throws InvalidSeatingDetailsException{

        //Find EventId in EventSeatingDetails, else throws InvalidSeatingDetailsException(eventId)
        EventSeatingDetails seatingDetails = seatingDetailsRepository.findEventSeatingDetailsByEventId(eventId)
                .orElseThrow(() -> new InvalidSeatingDetailsException(eventId));

        return SeatingDetailsResponse.builder()
                .seatingDetails(seatingDetails)
                .build();
    }

    //Public Helper methods
    /**
     * Update Seating Details for FindSeats
     *
     * @param eventId a String object containing the eventId of the event
     * @param category a String object containing the category of the event
     * @param seatsInformationString a String object containing the available seats of the event
     * @param numSeats an int containing the number of seats
     * @param eventDate a String object containing the eventDate of the event
     * @return a SeatingDetailsResponse object containing a seatingDetails object
     */
    public Response findAndUpdateSeatingDetails(String eventId, String category, String seatsInformationString,
                                                int numSeats, String eventDate)
            throws InvalidSeatingDetailsException{

        //Find EventId in EventSeatingDetails, else throws InvalidSeatingDetailsException(eventId)
        EventSeatingDetails eventSeatingDetails = seatingDetailsRepository.findEventSeatingDetailsByEventId(eventId)
                .orElseThrow(() -> new InvalidSeatingDetailsException(eventId));

        //Find Category
        Category cat = findCategoryFromEventSeatingDetails(eventSeatingDetails, category, eventDate);

        //Update Seats Information String and available seats
        cat.setSeatsInformationString(seatsInformationString);
        cat.setAvailableSeats(cat.getAvailableSeats() - numSeats);

        //Save the updated seatingDetails into the repository
        seatingDetailsRepository.save(eventSeatingDetails);

        return SeatingDetailsResponse.builder()
                .seatingDetails(eventSeatingDetails)
                .build();
    }

    /**
     * Update Seating Details for ConfirmSeats
     *
     * @param eventId a String object containing the eventId of the event
     * @param category a String object containing the category of the event
     * @param seatsInformationString a String object containing the available seats of the event
     * @param eventDate a String object containing the eventDate of the event
     * @return a SeatingDetailsResponse object containing a seatingDetails object
     */
    public Response confirmAndUpdateSeatingDetails(String eventId, String category, String seatsInformationString, String eventDate) {
        //Find EventId in EventSeatingDetails, else throws InvalidSeatingDetailsException(eventId)
        EventSeatingDetails eventSeatingDetails = seatingDetailsRepository.findEventSeatingDetailsByEventId(eventId)
                .orElseThrow(() -> new InvalidSeatingDetailsException(eventId));

        //Find Category
        Category cat = findCategoryFromEventSeatingDetails(eventSeatingDetails, category, eventDate);

        //Update Seats Information String
        cat.setSeatsInformationString(seatsInformationString);

        //Save the updated seatingDetails into the repository
        seatingDetailsRepository.save(eventSeatingDetails);

        return SeatingDetailsResponse.builder()
                .seatingDetails(eventSeatingDetails)
                .build();
    }

    /**
     * Update Seating Details for CancelSeats
     *
     * @param eventId a String object containing the eventId of the event
     * @param category a String object containing the category of the event
     * @param seatsInformationString a String object containing the available seats of the event
     * @param numSeats an int containing the number of seats
     * @param eventDate a String object containing the eventDate of the event
     * @return a SeatingDetailsResponse object containing a seatingDetails object
     */
    public Response deleteAndUpdateSeatingDetails(String eventId, String category, String seatsInformationString, int numSeats, String eventDate) {
        //Find EventId in EventSeatingDetails, else throws InvalidSeatingDetailsException(eventId)
        EventSeatingDetails eventSeatingDetails = seatingDetailsRepository.findEventSeatingDetailsByEventId(eventId)
                .orElseThrow(() -> new InvalidSeatingDetailsException(eventId));

        //Find Category
        Category cat = findCategoryFromEventSeatingDetails(eventSeatingDetails, category, eventDate);

        //Update Seats Information String and available seats
        cat.setSeatsInformationString(seatsInformationString);
        cat.setAvailableSeats(cat.getAvailableSeats() + numSeats);

        //Save the updated seatingDetails into the repository
        seatingDetailsRepository.save(eventSeatingDetails);

        return SeatingDetailsResponse.builder()
                .seatingDetails(eventSeatingDetails)
                .build();
    }

    /**
     * Find Category from eventSeatingDetails
     *
     * @param eventSeatingDetails a EventSeatingDetails object containing the eventSeatingDetails
     * @param category a String object containing the category of the event
     * @param eventDate a String object containing the eventDate of the event
     * @return a SeatingDetailsResponse object containing a seatingDetails object
     */
    public Category findCategoryFromEventSeatingDetails(EventSeatingDetails eventSeatingDetails, String category,
                                                        String eventDate)
            throws InvalidCategoryException {

        //Find listOfCategories in eventSeatingDetails
        List<Category> listOfCategory = eventSeatingDetails.getCategories();

        //Loops through each category in the listOfCategory
        for (int index = 0 ; index < listOfCategory.size(); index++) {
            Category c = listOfCategory.get(index);
            if (c.getCategory().equals(category) && c.getEventDate().equals(eventDate)) {
                return c;
            }
        }

        throw new InvalidCategoryException(category);
    }

    //Private Helper methods
    /**
     * Get EventSeatingDetails from request
     *
     * @param eventId a String object containing the eventId
     * @param request a SeatingDetailsRequest object containing the seatingDetailsRequest
     * @param isUpdate a boolean object containing the boolean of whether it is for an update
     * @return a SeatingDetailsResponse object containing a seatingDetails object
     */
    private EventSeatingDetails getEventSeatingDetailsFromRequest(String eventId, SeatingDetailsRequest request, boolean isUpdate)
            throws InvalidEventIdException, InvalidSeatingDetailsException, DuplicateSeatingDetailsException {

        // get the event
        Optional<Event> event = eventRepository.findById(eventId);

        //Checks if Event ID can be found in eventRepository
        if (event.isEmpty()) {
            throw new InvalidEventIdException(eventId);
        }

        // Determine all the different dates to have each category
        List<LocalDateTime> eventDates = event.get().getDates();

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

        List<Category> updatedCategories = new ArrayList<>();
        for (int i = 0 ; i < listOfCategory.size() ; i++) {

            //Obtain each Category object
            Category category = listOfCategory.get(i);

            //Assign the String to the category object
            category.setSeatsInformationString(stringBuilder.toString());

            //Assign the availSeat for all
            category.setAvailableSeats(totalSeats);

            // loop through each date of the event
            for (LocalDateTime date: eventDates) {
                // Assign each date to the category object
                category.setEventDate(date.toString());

                //Update the category in the list
                updatedCategories.add(category);
            }
        }

        //Generates a seatingDetails for the specific event using the details from both the requestBody and the eventId.
        EventSeatingDetails seatingDetails = EventSeatingDetails.builder()
                .eventId(eventId)
                .categories(updatedCategories)
                .build();
        return seatingDetails;
    }
}
