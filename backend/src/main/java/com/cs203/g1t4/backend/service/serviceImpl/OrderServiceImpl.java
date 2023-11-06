package com.cs203.g1t4.backend.service.serviceImpl;

import com.cs203.g1t4.backend.repository.OrderRepository;
import com.cs203.g1t4.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
//public class OrderServiceImpl implements OrderService {
public class OrderServiceImpl {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

//    // create the order object
//    public Response getOrder(String username, Seats[] seats)
}
