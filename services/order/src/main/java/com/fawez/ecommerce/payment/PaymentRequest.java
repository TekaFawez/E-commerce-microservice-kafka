package com.fawez.ecommerce.payment;

import com.fawez.ecommerce.customer.CustomerResponse;
import com.fawez.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
