package com.cs203.g1t4.backend.repository;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cs203.g1t4.backend.models.event.Event;


public interface EventRepository extends MongoRepository<Event, String>{

    Optional<Event> findById(String eventId);

    Optional<Event> findByArtistIdAndName(String ArtistId, String eventName);

//    Optional<List<Event>> findAllByTicketSalesDate(ArrayList<LocalDateTime> ticketSalesDate);

    // TO FIND ALL THE EVENTS OCCURRING AFTER TODAY'S DATE
    List<Event> findByDatesGreaterThan(LocalDateTime date);

    // To find all the events occurring between the two given dates
    List<Event> findByDatesBetween(LocalDateTime start, LocalDateTime end);

    // To update the event image name
//    void updateEventImageNameById(String eventId, String imageName);



    //TODO: WRITE THE QUERY FOR FINDING RANGE
    // Optional<List<Event>> findAllByTicketSalesDate(ArrayList<LocalDateTime> ticketSalesDate);

    void deleteById(String eventId);



}
