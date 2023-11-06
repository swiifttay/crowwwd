package com.cs203.g1t4.backend.service.serviceImpl;

import com.cs203.g1t4.backend.data.request.OrderRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.SingleOrderResponse;
import com.cs203.g1t4.backend.models.Order;
import com.cs203.g1t4.backend.models.exceptions.invalidIdException.InvalidOrderIdException;
import com.cs203.g1t4.backend.models.exceptions.invalidIdException.InvalidPaymentIdException;
import com.cs203.g1t4.backend.repository.OrderRepository;
import com.cs203.g1t4.backend.service.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public Response createOrder(@Valid OrderRequest orderRequest) {
        Order order = Order.builder()
                .category(orderRequest.getCategory())
                .eventDate(orderRequest.getEventDate())
                .payingUserId(orderRequest.getPayingUserId())
                .seats(orderRequest.getSeats())
                .eventId(orderRequest.getEventId())
                .pricePerSeat(orderRequest.getPricePerSeat())
                .totalCost(orderRequest.getTotalCost())
                .build();
        orderRepository.save(order);

        return SingleOrderResponse.builder()
                .order(order)
                .build();
    }

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
