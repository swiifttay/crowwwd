package com.cs203.g1t4.backend.data.response;

import com.cs203.g1t4.backend.models.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SingleOrderResponse implements Response {

    private Order order;

    public Order getOrder() {
        return order;
    }
}
