package com.cs203.g1t4.backend.data.request.fanRecord;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FanRecordRequest {

    @NotBlank(message = "Artist ID is required")
    String artistId;
}
