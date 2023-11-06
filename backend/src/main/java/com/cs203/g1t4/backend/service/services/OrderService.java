package com.cs203.g1t4.backend.service.services;

import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.SingleOrderResponse;
import com.cs203.g1t4.backend.models.Order;
import com.cs203.g1t4.backend.models.exceptions.InvalidOrderIdException;
import com.cs203.g1t4.backend.models.exceptions.InvalidPaymentIdException;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    Response updateOrder(String orderId, String paymentId);

    Response findByOrderId(String orderId);

    Response findByPaymentId(String paymentId);

    Response deleteByOrderId(String orderId);
}
