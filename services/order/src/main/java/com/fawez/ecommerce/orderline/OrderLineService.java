package com.fawez.ecommerce.orderline;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderLineService {
    private final OrderLineRepository repository;
    private final OrderLineMapper mapper;

    public Integer saveOrderLine(OrderLineRequest orderLineRequest) {
        return repository.save(mapper.toOrderLine(orderLineRequest)).getId();


    }

    public List<OrderLineResponse> findAllByOrderId(Integer orderId) {
        List<OrderLine> orderLineResponses= repository.findAllByOrderId(orderId);
        return orderLineResponses.stream().map(mapper::toOrderLineResponse).collect(Collectors.toList());

    }
}
