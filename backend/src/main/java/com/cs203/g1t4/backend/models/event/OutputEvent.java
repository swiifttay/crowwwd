package com.cs203.g1t4.backend.models.event;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OutputEvent {
  private String id;
  private String name;
  private String eventImageName; // imageName to look for in S3 Bucket
  private String description;
  private List<String> dates; // date and time of the actual concert
  private String venue;
  private List<String> categories;
  //To be changed to Artist
  private String artistId;
  private String seatingImagePlan; // imageName to look for in S3 Bucket
  private List<String> ticketSalesDate; // date and time at which the ticket sales will be available
  
}
