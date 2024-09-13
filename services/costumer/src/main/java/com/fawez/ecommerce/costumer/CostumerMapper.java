package com.fawez.ecommerce.costumer;

import org.springframework.stereotype.Service;

@Service
public class CostumerMapper {
    public Customer toCostumer(CustomerRequest request) {
        return Customer.builder()
                .id(request.id())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .address(request.address())
                .build();
    }

    public CustomerResponse toResponse(Customer customer) {
        return new  CustomerResponse(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getAddress()
        );
    }

}
