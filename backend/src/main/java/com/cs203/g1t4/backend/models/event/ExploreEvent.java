package com.cs203.g1t4.backend.models.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ExploreEvent {
    private String eventId;
    private String name;
    private String alias;
    private String eventImageName;
    private String eventImageURL; // Image URL
    private List<String> dates; // date and time of the actual concert
    private List<String> categories;
    private String artistName;
}
