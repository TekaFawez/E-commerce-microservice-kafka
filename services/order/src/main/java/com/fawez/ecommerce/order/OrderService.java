package com.fawez.ecommerce.order;

import com.fawez.ecommerce.customer.CustomerClient;
import com.fawez.ecommerce.exception.BusinessException;
import com.fawez.ecommerce.kafka.OrderConfirmation;
import com.fawez.ecommerce.kafka.OrderProducer;
import com.fawez.ecommerce.orderline.OrderLineRequest;
import com.fawez.ecommerce.orderline.OrderLineService;
import com.fawez.ecommerce.payment.PaymentClient;
import com.fawez.ecommerce.payment.PaymentRequest;
import com.fawez.ecommerce.product.ProductClient;
import com.fawez.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
private final OrderProducer orderProducer;
private final PaymentClient paymentClient;
    public Integer createOrder(OrderRequest request) {
        //check the costumer--open feign
        var customer=customerClient.findCostumerById(request.customerId())
                .orElseThrow(()-> new BusinessException(" cannot create order:: NO customer exist"));



        //purchase the product --> productMS(RestTemplate)
      var purchaseProducts=  this.productClient.purchaseProducts(request.products());


        //persist order
        var order=repository.save(mapper.toOrder(request));

        //persist orderline
        for(PurchaseRequest purchaseRequest: request.products()){
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );

        }

        //to do start the payments
        paymentClient.requestOrderPayment(
                new PaymentRequest(
                        request.amount(),
                        request.paymentMethod(),
                        order.getId(),
                        order.getReference(),
                        customer
                )
        );

        //send the order confirmation (kafka)
        this.orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchaseProducts
                )
        );
        return order.getId();

    }

    public List<OrderResponse> findAllOrders() {
        return repository.findAll().stream()
                .map(mapper::fromOrder).collect(Collectors.toList());
    }

    public OrderResponse findById(Integer id) {
        return this.repository.findById(id)
                .map(this.mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with the provided ID: %d", id)));
    }
}
