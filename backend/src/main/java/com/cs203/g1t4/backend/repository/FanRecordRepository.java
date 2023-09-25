package com.cs203.g1t4.backend.repository;

import com.cs203.g1t4.backend.models.FanRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface FanRecordRepository extends MongoRepository<FanRecord, String> {

    Optional<FanRecord> findFanRecordById(String id);

    List<FanRecord> findFanRecordByUserId(String userId);

    Optional<FanRecord> findFanRecordByUserIdAndArtistId(String userId, String artistId);
}
