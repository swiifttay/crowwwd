package com.cs203.g1t4.backend.data.request.seat;

import com.cs203.g1t4.backend.data.request.ticket.TicketRequest;
import com.cs203.g1t4.backend.models.Category;
import com.cs203.g1t4.backend.models.User;
import com.cs203.g1t4.backend.models.event.Event;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeatsConfirmRequest {

    private List<String> allocatedSeats;
    private List<String> userIdsAttending;
    private int noOfSurpriseTickets;

    public List<TicketRequest> returnTicketRequestListFromRequest(User user, String eventId) {

        if ((userIdsAttending.size() + noOfSurpriseTickets) != allocatedSeats.size()) {
            throw new IllegalArgumentException();
        }

        List<TicketRequest> list = new ArrayList<>();
        for (int i = 0 ; i < allocatedSeats.size() ; i++) {
            String seatNo = allocatedSeats.get(i);
            String userIdAttending = (i < userIdsAttending.size()) ? userIdsAttending.get(i) : null;
            boolean isSurpriseTicket = !(i < userIdsAttending.size());
            TicketRequest ticketRequest = TicketRequest.builder()
                    .seatNo(seatNo)
                    .userIdAttending(userIdAttending)
                    .eventId(eventId)
                    .isSurpriseTicket(isSurpriseTicket)
                    .build();
            list.add(ticketRequest);
        }
        return list;
    }
}
