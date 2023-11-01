package com.cs203.g1t4.backend.service;

import com.cs203.g1t4.backend.data.request.event.SeatingDetailsRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SeatingDetailsService {

    public SuccessResponse addSeatingDetails(String eventId, SeatingDetailsRequest request);

    public Response deleteSeatingDetails(String eventId);
    
    public Response updateSeatingDetails(String eventId, SeatingDetailsRequest request);

    public Response getSeatingDetailsById(String eventId);

    //Public Helper methods
    public Response updateSeatingDetails(String eventId, String category, String seatsInformationString);
}
