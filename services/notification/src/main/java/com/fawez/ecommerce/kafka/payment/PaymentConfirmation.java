package com.fawez.ecommerce.kafka.payment;

import java.math.BigDecimal;
//it's the same record from MS Payment : class Payment Notification request
public record PaymentConfirmation(
        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerFirstname,
        String customerLastname,
        String customerEmail
) {
}
