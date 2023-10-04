package com.cs203.g1t4.backend.models.event;

import com.cs203.g1t4.backend.models.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("eventSeatingDetails")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EventSeatingDetails {
    @Id
    private String eventId;

    @NotBlank
    private List<Category> categories;
}
