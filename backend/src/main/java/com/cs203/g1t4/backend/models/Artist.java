package com.cs203.g1t4.backend.models;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document("artist")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Artist {
    @Id
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String website;

    @NotBlank
    private String artistImage;

    @NotBlank
    private String description;
}
