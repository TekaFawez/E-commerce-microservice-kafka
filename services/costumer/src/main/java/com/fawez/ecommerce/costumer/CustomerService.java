package com.fawez.ecommerce.costumer;

import com.fawez.ecommerce.exception.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CostumerRepository repository;
    private final CostumerMapper mapper;
    public String createCostumer(CustomerRequest request) {
        Customer save =repository.save(mapper.toCostumer(request));
        return save.getId();
    }

    public void updateCostumer(CustomerRequest request) {
        Customer update=repository.findById(request.id())
                .orElseThrow(()->new CustomerNotFoundException(
                        String.format("cannot update costumer:: No Id %s",request.id())

                ) );
        mergeCustomer(update,request);
        repository.save(update);



    }

    private void mergeCustomer(Customer customer, CustomerRequest request) {
        if (StringUtils.isNotBlank(request.firstName())) {
            customer.setFirstName(request.firstName());
        }
        if (StringUtils.isNotBlank(request.email())) {
            customer.setEmail(request.email());
        }
        if (request.address() != null) {
            customer.setAddress(request.address());
        }
    }

    public List<CustomerResponse> findAllCustomers() {
        List<Customer> customers = repository.findAll();
        return customers.stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    public Boolean existsById(String customerId) {
        return repository.findById(customerId)
                .isPresent();
    }

    public CustomerResponse findById(String customerId) {
        Customer customer=repository.findById(customerId)
                .orElseThrow(()-> new CustomerNotFoundException(
                        String.format("No Customer found %s",customerId)
                ));
        return mapper.toResponse(customer);
    }

    public void deleteCustomer(String id) {
        this.repository.deleteById(id);
    }
}
