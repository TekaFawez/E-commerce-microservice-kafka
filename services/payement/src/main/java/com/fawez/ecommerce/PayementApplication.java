package com.fawez.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PayementApplication {

	public static void main(String[] args) {
		SpringApplication.run(PayementApplication.class, args);
	}

}
