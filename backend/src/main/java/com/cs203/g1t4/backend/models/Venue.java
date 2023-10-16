package com.cs203.g1t4.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("venue")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Venue {
  @Id
    private String id;

    @NotBlank
    private String locationName;

    @NotBlank
    private String address;

    @NotBlank
    private String postalCode;

    @NotBlank
    private String description;
  
}
