package com.cs203.g1t4.backend.data.response.ticket;

import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.models.Ticket;
import com.cs203.g1t4.backend.models.event.TicketEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FullSingleTicketResponse implements Response {
    private TicketEvent ticketEvent;
    private Ticket ticket;
}
