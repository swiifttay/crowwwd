package com.cs203.g1t4.backend.controller;

import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.service.services.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class OrderController {

    private final OrderService orderService;

    @PutMapping("/order/{orderId}/payment/{paymentId}")
    public ResponseEntity<Response> updateOrder(@PathVariable String orderId, @PathVariable String paymentId) {

        Response response = orderService.updateOrder(orderId, paymentId);

        //Else, return ok response
        return ResponseEntity.ok(response);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Response> findOrderByOrderId(@PathVariable String orderId) {

        Response response = orderService.findByOrderId(orderId);

        //If successful, the response is encapsulated with HTTP code of 200(ok) and contains the User object
        return ResponseEntity.ok(response);
    }

    @GetMapping("/orderPayment/{paymentId}")
    public ResponseEntity<Response> findOrderByPaymentId(@PathVariable String paymentId) {

        Response response = orderService.findByPaymentId(paymentId);

        //If successful, the response is encapsulated with HTTP code of 200(ok) and contains the User object
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<Response> deleteOrderByOrderId(@PathVariable String orderId) {

        Response response = orderService.deleteByOrderId(orderId);

        //If successful, the response is encapsulated with HTTP code of 200(ok) and contains the User object
        return ResponseEntity.ok(response);
    }
}
