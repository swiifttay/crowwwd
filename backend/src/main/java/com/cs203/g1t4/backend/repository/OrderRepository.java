package com.cs203.g1t4.backend.repository;

import com.cs203.g1t4.backend.models.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, String> {
    Optional<Order> findById(String id);

    Optional<Order> findByPaymentId(String paymentId);

    void deleteById(String id);

    Optional<Order> findByPayingUserIdAndEventIdAndEventDate(String payingUserId, String eventId, String eventDate);

}
