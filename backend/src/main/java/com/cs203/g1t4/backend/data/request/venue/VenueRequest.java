package com.cs203.g1t4.backend.data.request.venue;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VenueRequest {


    @NotNull
    private String locationName;

    @NotNull
    private String address;

    @NotNull
    private String postalCode;
}
