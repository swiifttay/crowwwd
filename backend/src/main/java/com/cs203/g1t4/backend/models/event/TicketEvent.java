package com.cs203.g1t4.backend.models.event;

import com.cs203.g1t4.backend.models.Artist;
import com.cs203.g1t4.backend.models.Venue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TicketEvent {
    private String eventId;
    private String eventName;
    private Artist artist;
    private String venueName;
}
