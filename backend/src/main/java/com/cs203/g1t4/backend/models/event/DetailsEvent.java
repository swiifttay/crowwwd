package com.cs203.g1t4.backend.models.event;

import com.cs203.g1t4.backend.models.Venue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DetailsEvent {
    private String eventId;
    private String name;
    private String eventImageName;
    private String eventImageURL; // Image URL
    private List<String> dates; // date and time of the actual concert
    private List<String> ticketSalesDate; // date and time at which the ticket sales will be available
    private Venue venue;
    private String description;
}
