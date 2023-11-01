package com.cs203.g1t4.backend.service;

import com.cs203.g1t4.backend.data.request.ticket.TicketRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.ticket.SingleTicketResponse;
import org.springframework.stereotype.Service;

@Service
public interface TicketService {

    public SingleTicketResponse createTicket(TicketRequest ticketRequest, String username);

    public Response deleteTicket(String ticketId);

    public Response updateTicket(String ticketId, TicketRequest ticketRequest, String username);

    public Response getTicketById(String ticketId);

    public Response getTicketsByUser(String username);

    public Response getTicketByEvent(String eventId);
}
