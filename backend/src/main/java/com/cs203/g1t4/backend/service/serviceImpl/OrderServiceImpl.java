package com.cs203.g1t4.backend.service.serviceImpl;

import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.SingleOrderResponse;
import com.cs203.g1t4.backend.models.Order;
import com.cs203.g1t4.backend.models.exceptions.InvalidOrderIdException;
import com.cs203.g1t4.backend.models.exceptions.InvalidPaymentIdException;
import com.cs203.g1t4.backend.repository.OrderRepository;
import com.cs203.g1t4.backend.service.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public Response updateOrder(String orderId, String paymentId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new InvalidOrderIdException(orderId));
        order.setPaymentId(paymentId);
        orderRepository.save(order);

        return SingleOrderResponse.builder()
                .order(order)
                .build();
    }

    public Response findByOrderId(String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new InvalidOrderIdException(orderId));
        return SingleOrderResponse.builder()
                .order(order)
                .build();
    }

    public Response findByPaymentId(String paymentId) {
        Order order = orderRepository.findByPaymentId(paymentId).orElseThrow(() -> new InvalidPaymentIdException(paymentId));
        return SingleOrderResponse.builder()
                .order(order)
                .build();
    }

    public Response deleteByOrderId(String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new InvalidOrderIdException(orderId));
        orderRepository.deleteById(orderId);
        return SingleOrderResponse.builder()
                .order(order)
                .build();
    }
}
