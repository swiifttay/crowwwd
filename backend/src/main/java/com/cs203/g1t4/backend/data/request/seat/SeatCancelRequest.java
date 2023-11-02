package com.cs203.g1t4.backend.data.request.seat;

import com.cs203.g1t4.backend.data.response.Response;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeatCancelRequest implements Response {
    @NotBlank
    private String eventId;
    @NotBlank
    private String category;

    private List<String> allocatedSeats;
}
