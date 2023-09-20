package com.cs203.g1t4.backend.data.request.ticket;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketRequest {

    @NotNull
    private String eventId;

    @NotNull
    private String userIdAttending;


    @NotNull
    private String seatNo;

    @NotNull
    private boolean isSurpriseTicket;
}
