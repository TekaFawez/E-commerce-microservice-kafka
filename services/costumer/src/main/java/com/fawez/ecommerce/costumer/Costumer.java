package com.fawez.ecommerce.costumer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document
public class Costumer {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private Address address;
}
