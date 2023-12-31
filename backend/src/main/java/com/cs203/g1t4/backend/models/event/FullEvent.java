package com.cs203.g1t4.backend.models.event;

import com.cs203.g1t4.backend.models.Artist;
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
public class FullEvent {
  private String eventId;
  private String alias;
  private String name;
  private String eventImageName;
  private String eventImageURL; // Image URL
  private String description;
  private List<String> dates; // date and time of the actual concert
  private Venue venue;
  private List<String> categories;
  private Artist artist;
  private String seatingImagePlan; // imageName to look for in S3 Bucket
  private List<String> ticketSalesDate; // date and time at which the ticket sales will be available
  
}
