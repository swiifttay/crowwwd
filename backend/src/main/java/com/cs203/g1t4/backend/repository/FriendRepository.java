package com.cs203.g1t4.backend.repository;

import com.cs203.g1t4.backend.models.Friendship;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends MongoRepository<Friendship, String> {
    Optional<Friendship> findByUserId(String userId);

}
