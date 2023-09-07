package com.cs203.g1t4.backend.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cs203.g1t4.backend.models.Event;



public interface EventRepository extends MongoRepository<Event, String>{
    Optional<Event> findByName(String name);
    Optional<List<Event>> findAllByTicketSalesDate(ArrayList<LocalDateTime> ticketSalesDate);

    Optional<Event> findByArtistIdAndName(String ArtistId, String eventName);

    void deleteById(String eventId);
 
}
