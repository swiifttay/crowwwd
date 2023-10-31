package com.cs203.g1t4.backend.repository;

import com.cs203.g1t4.backend.models.Friend;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends MongoRepository<Friend, String> {
    Optional<Friend> findByUserIdAndFriendId(String userId, String friendId);

    List<Friend> findAllByUserId(String userId);

    List<Friend> findAllByUserIdAndApproved(String userId, boolean isApproved);

    void deleteByUserIdAndFriendId(String userId, String friendId);
}
