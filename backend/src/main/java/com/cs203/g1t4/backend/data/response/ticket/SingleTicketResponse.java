package com.cs203.g1t4.backend.data.response.ticket;

import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.models.Ticket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SingleTicketResponse implements Response {

    private Ticket ticket;
}
