package com.cs203.g1t4.backend.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cs203.g1t4.backend.models.Venue;

public interface VenueRepository extends MongoRepository<Venue, String> {
  Optional<Venue> findById(String venueId);
}
