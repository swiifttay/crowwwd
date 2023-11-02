package com.cs203.g1t4.backend.repository;

import com.cs203.g1t4.backend.models.queue.QueueStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface QueueStatusRepository extends MongoRepository<QueueStatus, String> {

    Optional<QueueStatus> findById(String id);

    Optional<QueueStatus> findQueueByUserIdAndEventId(String userId, String eventId);

}
