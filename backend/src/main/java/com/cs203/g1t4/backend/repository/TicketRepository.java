package com.cs203.g1t4.backend.repository;

import com.cs203.g1t4.backend.models.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends MongoRepository<Ticket, String> {
    Optional<Ticket> findById(String ticketId);

    List<Ticket> findAllByUserIdAttending(String userIdAttending);

    Optional<Ticket> findByEventId(String eventId);
}
