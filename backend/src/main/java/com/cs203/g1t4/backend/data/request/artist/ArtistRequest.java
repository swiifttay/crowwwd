package com.cs203.g1t4.backend.data.request.artist;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArtistRequest {
    @NotNull
    private String name;
    @NotNull
    private String website;
    @NotNull
    private String artistImage;
    @NotNull
    private String description;
}
