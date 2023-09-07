package com.cs203.g1t4.backend.repository;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cs203.g1t4.backend.models.Event;



public interface EventRepository extends MongoRepository<Event, String>{

    // Find events by its Name: May not be exactly accurate as different artist can have events of the same name
    Optional<Event> findByName(String eventName);

    // Find events by its Name: May not be exactly accurate as different artist can have events of the same name
    Optional<Event> findById(String eventId);

    Optional<Event> findByArtistIdAndName(String ArtistId, String eventName);

    Optional<List<Event>> findAllByTicketSalesDate(ArrayList<LocalDateTime> ticketSalesDate);

    // TO FIND ALL THE EVENTS OCCURING AFTER TODAY'S DATE
    Optional<List<Event>> findAllAfterDate(LocalDateTime date);

    //TODO: WRITE THE QUERY FOR FINDING RANGE
    // Optional<List<Event>> findAllByTicketSalesDate(ArrayList<LocalDateTime> ticketSalesDate);

    void deleteById(String eventId);



}
