package com.fawez.ecommerce.costumer;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CostumerRepository extends MongoRepository<Customer,String> {
}
