package com.cs203.g1t4.backend.data.response.ticket;

import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.models.FullTicket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FullTicketResponse implements Response {
    private List<FullTicket> fullTickets;
}
