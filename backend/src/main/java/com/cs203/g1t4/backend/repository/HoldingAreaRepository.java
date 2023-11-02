package com.cs203.g1t4.backend.repository;

import com.cs203.g1t4.backend.models.queue.HoldingArea;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface HoldingAreaRepository extends MongoRepository<HoldingArea, String> {
  
  Optional<HoldingArea> findHoldingAreaById(String id);

  Optional<HoldingArea> findHoldingAreaByEventId(String eventId);

}
