package com.cs203.g1t4.backend.data.request.fanRecord;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FanRecordRequest {
    @NotNull
    String artistId;
}
