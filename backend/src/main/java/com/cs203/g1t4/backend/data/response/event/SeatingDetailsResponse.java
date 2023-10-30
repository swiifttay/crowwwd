package com.cs203.g1t4.backend.data.response.event;

import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.models.event.EventSeatingDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeatingDetailsResponse implements Response {

    private EventSeatingDetails seatingDetails;
}
