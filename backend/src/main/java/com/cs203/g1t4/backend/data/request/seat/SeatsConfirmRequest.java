package com.cs203.g1t4.backend.data.request.seat;

import com.cs203.g1t4.backend.data.request.ticket.TicketRequest;
import com.cs203.g1t4.backend.models.Order;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Order ID should not be blank")
    private String orderId;

    @NotBlank(message = "Payment ID should not be blank")
    private String paymentId;

    private List<String> userIdsAttending;

    private int noOfSurpriseTickets;

    public List<TicketRequest> returnTicketRequestListFromRequest(String eventId, Order order) {

        String[] allocatedSeats = order.getSeats();

        if ((userIdsAttending.size() + noOfSurpriseTickets) != allocatedSeats.length) {
            throw new IllegalArgumentException();
        }

        List<TicketRequest> list = new ArrayList<>();
        for (int i = 0 ; i < allocatedSeats.length ; i++) {
            String seatNo = allocatedSeats[i];
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
