package com.fawez.ecommerce.kafka;

import com.fawez.ecommerce.customer.CustomerResponse;
import com.fawez.ecommerce.order.PaymentMethod;
import com.fawez.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(

        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products


) {
}
