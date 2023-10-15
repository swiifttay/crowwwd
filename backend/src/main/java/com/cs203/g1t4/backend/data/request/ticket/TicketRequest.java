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
public class TicketRequest {

    @NotBlank(message = "Event ID is required")
    private String eventId;

    @NotBlank(message = "Attending userID is required")
    private String userIdAttending;

//    @NotBlank(message = "Seat Number is required")
//    private String seatNo;

    //If not filled, assume false.
    private boolean isSurpriseTicket;
}
