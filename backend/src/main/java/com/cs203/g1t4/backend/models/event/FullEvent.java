package com.cs203.g1t4.backend.models.event;

import com.cs203.g1t4.backend.models.Artist;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FullEvent {
  private String eventId;
  private String name;
  private String eventImageName;
  private String eventImageURL; // Image URL
  private String description;
  private List<String> dates; // date and time of the actual concert
  private String venue;
  private List<String> categories;
  private Artist artist;
  private String seatingImagePlan; // imageName to look for in S3 Bucket
  private List<String> ticketSalesDate; // date and time at which the ticket sales will be available
  
}
