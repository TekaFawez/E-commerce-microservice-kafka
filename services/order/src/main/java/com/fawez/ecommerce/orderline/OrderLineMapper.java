package com.fawez.ecommerce.orderline;

import com.fawez.ecommerce.order.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper {
    public OrderLine toOrderLine(OrderLineRequest orderLineRequest) {
        return OrderLine.builder()
                .id(orderLineRequest.id())
                .order(Order.builder()
                        .id(orderLineRequest.orderId())
                        .build())
                .quantity(orderLineRequest.quantity())
                .productId(orderLineRequest.productId())

                .build();
    }

    public OrderLineResponse toOrderLineResponse(OrderLine orderLine) {
        return new OrderLineResponse(
                orderLine.getId(),
                orderLine.getQuantity()
        );

    }
}
