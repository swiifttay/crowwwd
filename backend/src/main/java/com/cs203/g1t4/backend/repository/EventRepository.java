package com.cs203.g1t4.backend.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cs203.g1t4.backend.models.Event;



public interface EventRepository extends MongoRepository<Event, String>{
  // TO FIND THE EVENT BY ITS NAME
    Optional<List<Event>> findByName(String name);

    //TODO: WRITE THE QUERY FOR FINDING RANGE
    // Optional<List<Event>> findAllByTicketSalesDate(ArrayList<LocalDateTime> ticketSalesDate);

    // TO FIND ALL THE EVENTS OCCURING AFTER TODAY'S DATE
    Optional<List<Event>> findAllAfterDate(LocalDateTime date);
 
}
