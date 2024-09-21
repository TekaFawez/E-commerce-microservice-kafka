package com.fawez.ecommerce.kafka;

import com.fawez.ecommerce.email.EmailService;
import com.fawez.ecommerce.kafka.order.OrderConfirmation;
import com.fawez.ecommerce.kafka.payment.PaymentConfirmation;
import com.fawez.ecommerce.notifaction.Notification;
import com.fawez.ecommerce.notifaction.NotificationRepository;
import com.fawez.ecommerce.notifaction.NotificationType;
import jakarta.mail.MessagingException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@Data
public class NotificationConsumer {
    private final NotificationRepository repository;
   private final EmailService emailService;
@KafkaListener(topics = "payment-topic")
    public void ConsumePaymentSuccessNotification(PaymentConfirmation paymentConfirmation) throws MessagingException {
log.info(String.format("consuming the msg from payment-topic::%s",paymentConfirmation));
  repository.save(  Notification.builder()
            .type(NotificationType.PAYMENT_CONFIRMATION)
            .notificationDate(LocalDateTime.now())
            .paymentConfirmation(paymentConfirmation)
                        .build()


);
 var customerName=paymentConfirmation.customerFirstname()+paymentConfirmation.customerLastname();
 emailService.sendPaymentSuccessEmail(
         paymentConfirmation.customerEmail(),
         customerName,
         paymentConfirmation.amount(),
         paymentConfirmation.orderReference()
 );
    }

    @KafkaListener(topics = "order-topic")
    public void ConsumeOrderConfirmationNotification(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info(String.format("consuming the msg from order-topic::%s",orderConfirmation));
        repository.save(  Notification.builder()
                .type(NotificationType.PAYMENT_CONFIRMATION)
                .notificationDate(LocalDateTime.now())
                .orderConfirmation(orderConfirmation)
                .build()


        );
        var customerName=orderConfirmation.customer().firstname()+" " +orderConfirmation.customer().lastname();
        emailService.sendOrderConfirmationEmail(
                orderConfirmation.customer().email(),
                customerName,
                orderConfirmation.totalAmount(),
                orderConfirmation.orderReference(),
                orderConfirmation.products()
        );
    }


}
