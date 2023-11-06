package com.cs203.g1t4.backend.service.services;

import com.cs203.g1t4.backend.data.request.OrderRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.SingleOrderResponse;
import com.cs203.g1t4.backend.models.Order;
import com.cs203.g1t4.backend.models.exceptions.invalidIdException.InvalidOrderIdException;
import com.cs203.g1t4.backend.models.exceptions.invalidIdException.InvalidPaymentIdException;

import org.springframework.stereotype.Service;

@Service
public interface OrderService {

    Response createOrder(OrderRequest orderRequest);

    Response updateOrder(String orderId, String paymentId);

    Response findByOrderId(String orderId);

    Response findByPaymentId(String paymentId);

    Response deleteByOrderId(String orderId);
}
