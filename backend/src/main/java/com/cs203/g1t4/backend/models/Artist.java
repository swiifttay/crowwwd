package com.cs203.g1t4.backend.models;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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

//    @NotBlank
    private String artistImage;

//    @NotBlank
    private String artistImageURL;

//    @NotBlank
    private String description;

//    public boolean equals(Artist another) {
//        return this.name == another.getName()
//                && this.website == another.getWebsite()
//                && this.artistImageURL == another.getArtistImageURL()
//                && this.description == another.getDescription();
//    }
}
