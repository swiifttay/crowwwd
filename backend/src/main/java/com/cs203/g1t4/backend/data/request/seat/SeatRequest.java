package com.cs203.g1t4.backend.data.request.seat;

import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.models.Seats;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeatRequest implements Response {
  private List<String> allocatedSeats;
}
