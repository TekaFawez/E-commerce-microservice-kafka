package com.fawez.ecommerce.order;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderMapper {
    public Order toOrder(OrderRequest request){
        return  Order.builder()
                .id(request.id())
                .reference(request.reference())
                .customerId(request.customerId())
                .totalAmount(request.amount())
                .paymentMethod(request.paymentMethod())
                .build();
    }

    public OrderResponse fromOrder(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getCustomerId(),
                order.getTotalAmount(),
                order.getPaymentMethod(),
                order.getReference()

        );
    }
}
