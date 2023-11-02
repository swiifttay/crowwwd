package com.cs203.g1t4.backend.models.queue;

import com.cs203.g1t4.backend.models.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document("holdingArea")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class HoldingArea {

  @Id
  private String id;

  @NotBlank
  private String eventId;

  @NotBlank
  private List<String> fans;

  @NotBlank
  private List<String> regulars;

  @NotBlank
  private boolean isEarlyHolder;

  @NotBlank
  private Integer queuesToPurchase;

  @NotBlank
  private Integer queuesMade;

  @NotBlank
  private LocalDateTime lastQueueCreateTime;

  @NotBlank
  private LocalDateTime lastQueueMoveToPurchaseTime;

}
