package com.cs203.g1t4.backend.data.request.ticket;

import com.cs203.g1t4.backend.models.Ticket;
import com.cs203.g1t4.backend.models.User;
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

    @NotBlank(message = "Event ID is required")
    private String eventId;

    @NotBlank(message = "Attending userID is required")
    private String userIdAttending;

    @NotBlank
    private String seatNo;

    //If not filled, assume false.
    private boolean isSurpriseTicket;

    public Ticket returnTicketFromRequest(User user) {
        return Ticket.builder()
                .userIdAttending(this.getUserIdAttending())
                .eventId(this.getEventId())
                .userIdBuyer(user.getId())
                .seatNo(this.seatNo)
                .isSurpriseTicket(this.isSurpriseTicket())
                .build();
    }


}
