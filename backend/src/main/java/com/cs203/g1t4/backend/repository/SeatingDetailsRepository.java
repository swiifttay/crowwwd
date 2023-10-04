package com.cs203.g1t4.backend.repository;

import com.cs203.g1t4.backend.models.FanRecord;
import com.cs203.g1t4.backend.models.event.Event;
import com.cs203.g1t4.backend.models.event.EventSeatingDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SeatingDetailsRepository extends MongoRepository<EventSeatingDetails, String> {

    Optional<EventSeatingDetails> findEventSeatingDetailsByEventId(String id);
}
