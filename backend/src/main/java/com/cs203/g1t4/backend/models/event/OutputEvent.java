package com.cs203.g1t4.backend.models.event;


import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Document("event")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OutputEvent {
  private String id;
  private String name;
  private String eventImageName; // imageName to look for in S3 Bucket
  private String description;

  @NotBlank
  private List<LocalDateTime> dates; // date and time of the actual concert
  
  @NotBlank
  private String venue;
  
  @NotBlank
  private List<String> categories;
  
  @NotBlank
  private String artistId;
  
  @NotBlank
  private String seatingImagePlan; // imageName to look for in S3 Bucket
  
  @NotBlank
  private List<LocalDateTime> ticketSalesDate; // date and time at which the ticket sales will be available
  
}
