package com.cs203.g1t4.backend.data.request.ticket;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTicketRequest {

    @NotBlank
    private String eventId;

    @NotBlank
    private String userIdAttending;


    @NotBlank
    private String seatNo;

    @NotBlank
    private boolean isSurpriseTicket;
}
