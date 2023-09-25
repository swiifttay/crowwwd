package com.cs203.g1t4.backend.repository;

import com.cs203.g1t4.backend.models.Artist;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface ArtistRepository extends MongoRepository<Artist, String> {
    Optional<Artist> findById(String id);

    Optional<Artist> findByName(String name);
}
