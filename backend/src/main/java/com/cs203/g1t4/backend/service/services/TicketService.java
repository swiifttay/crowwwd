package com.cs203.g1t4.backend.service.services;

import com.cs203.g1t4.backend.data.request.ticket.TicketRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.ticket.SingleTicketResponse;
import org.springframework.stereotype.Service;

@Service
public interface TicketService {

    SingleTicketResponse createTicket(TicketRequest ticketRequest, String username);

    Response deleteTicket(String ticketId);

    Response updateTicket(String ticketId, TicketRequest ticketRequest, String username);

    Response getTicketById(String ticketId);

    Response getTicketsByUser(String username);

    Response getTicketByEvent(String eventId);
}
