package com.cs203.g1t4.backend.service.services;

import com.cs203.g1t4.backend.data.request.event.SeatingDetailsRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SeatingDetailsService {

    SuccessResponse addSeatingDetails(String eventId, SeatingDetailsRequest request);

    Response deleteSeatingDetails(String eventId);
    
    Response updateSeatingDetails(String eventId, SeatingDetailsRequest request);

    Response getSeatingDetailsById(String eventId);

    //Public Helper methods
    Response updateSeatingDetails(String eventId, String category, String seatsInformationString);
}
