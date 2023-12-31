package com.cs203.g1t4.backend.data.response.seat;

import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.models.Seats;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeatResponse implements Response {
  private Seats seats;
}
