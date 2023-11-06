package com.cs203.g1t4.backend.data.response.venue;

import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.models.Venue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SingleVenueResponse implements Response {
  private Venue venue;
  private String imageURL;
}
