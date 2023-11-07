package com.cs203.g1t4.backend.service.services;

import com.cs203.g1t4.backend.data.request.event.SeatingDetailsRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.models.Category;
import com.cs203.g1t4.backend.models.event.EventSeatingDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SeatingDetailsService {

    SuccessResponse addSeatingDetails(String eventId, SeatingDetailsRequest request);

    Response deleteSeatingDetails(String eventId);
    
    Response updateSeatingDetails(String eventId, SeatingDetailsRequest request);

    Response getSeatingDetailsById(String eventId);

    Category findCategoryFromEventSeatingDetails(EventSeatingDetails eventSeatingDetails, String category, String eventDate);

    //Public Helper methods
    Response findAndUpdateSeatingDetails(String eventId, String category, String seatsInformationString, int numSeats, String eventDate);

    Response confirmAndUpdateSeatingDetails(String eventId, String category, String seatsInformationString, String eventDate);

    Response deleteAndUpdateSeatingDetails(String eventId, String category, String seatsInformationString, int numSeats, String eventDate);
}
