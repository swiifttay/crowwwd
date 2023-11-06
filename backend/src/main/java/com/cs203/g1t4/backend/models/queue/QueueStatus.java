package com.cs203.g1t4.backend.models.queue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("queueStatus")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class QueueStatus {

  @Id
  private String id;

  // keep track of the user id
  @NotBlank
  private String userId;

  // determine if user is in queue
  @NotBlank
  private QueueingStatusValues queueStatus;

  // take note of the event id
  @NotBlank
  private String eventId;

  // can be blank depending on if user is in the queue
  // if not blank, this records the queue number of the user (in batches)
  private Integer queueId;

  private boolean isFan;
  
}
