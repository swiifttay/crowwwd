package com.cs203.g1t4.backend.service.serviceImpl;

import com.cs203.g1t4.backend.data.request.OrderRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.SingleOrderResponse;
import com.cs203.g1t4.backend.models.Order;
import com.cs203.g1t4.backend.models.exceptions.InvalidOrderIdException;
import com.cs203.g1t4.backend.models.exceptions.InvalidPaymentIdException;
import com.cs203.g1t4.backend.repository.OrderRepository;
import com.cs203.g1t4.backend.service.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    /**
     * Create Order from request
     *
     * @param orderRequest a OrderRequest object containing orderDetails
     * @return a SingleOrderResponse object containing an order object
     */
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

    /**
     * Update paymentId in Order
     *
     * @param orderId a String object containing orderId
     * @param paymentId a String object containing the paymentId
     * @return a SingleOrderResponse object containing an order object
     */
    public Response updateOrder(String orderId, String paymentId) throws InvalidOrderIdException {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new InvalidOrderIdException(orderId));
        order.setPaymentId(paymentId);
        orderRepository.save(order);

        return SingleOrderResponse.builder()
                .order(order)
                .build();
    }

    /**
     * Get Order from orderId
     *
     * @param orderId a String object containing orderId
     * @return a SingleOrderResponse object containing an order object
     */
    public Response findByOrderId(String orderId) throws InvalidOrderIdException {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new InvalidOrderIdException(orderId));
        return SingleOrderResponse.builder()
                .order(order)
                .build();
    }

    /**
     * Get Order from paymentId
     *
     * @param paymentId a String object containing paymentId
     * @return a SingleOrderResponse object containing an order object
     */
    public Response findByPaymentId(String paymentId) throws InvalidPaymentIdException{
        Order order = orderRepository.findByPaymentId(paymentId).orElseThrow(() -> new InvalidPaymentIdException(paymentId));
        return SingleOrderResponse.builder()
                .order(order)
                .build();
    }

    /**
     * Delete Order using orderId
     *
     * @param orderId a String object containing orderId
     * @return a SingleOrderResponse object containing an order object
     */
    public Response deleteByOrderId(String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new InvalidOrderIdException(orderId));
        orderRepository.deleteById(orderId);
        return SingleOrderResponse.builder()
                .order(order)
                .build();
    }
}
