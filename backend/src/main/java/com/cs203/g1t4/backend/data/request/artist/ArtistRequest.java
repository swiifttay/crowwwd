package com.cs203.g1t4.backend.data.request.artist;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArtistRequest {

    @NotBlank(message = "Artist Name is required")
    private String name;

    @NotBlank(message = "Artist Website is required")
    private String website;

    //Is there a missing artistURL here?

    //Can be blank as not all artist may have images
//    private String artistImage;oh r

    //Can be blank as not all artist may have description planned
    //Consider size validation checks for inputted description.
    private String description;
}
