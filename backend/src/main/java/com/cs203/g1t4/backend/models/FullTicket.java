package com.cs203.g1t4.backend.models;

import com.cs203.g1t4.backend.models.event.TicketEvent;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FullTicket {

    @Id
    private String id;

    @NotBlank
    private TicketEvent ticketEvent;

    @NotBlank
    private String userIdAttending;

    @NotBlank
    private String userIdBuyer;

    @NotBlank
    private String seatNo;  // to adjust in the future to fit the seating algorithm

    @NotBlank
    private boolean isSurpriseTicket;

}
