package com.cs203.g1t4.backend.data.response.event;

import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.models.event.ExploreEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExploreEventsResponse implements Response {
    private List<ExploreEvent> exploreEventList;
}
