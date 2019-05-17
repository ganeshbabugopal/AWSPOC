package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class OrderDomainApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderDomainApplication.class, args);
	}

	@RequestMapping(value="/order", method=RequestMethod.POST)
	public String createOrder(@RequestBody OrderDAO order) {
		
		return "Domain - Order created " + order.getProductName();
	}
	
	
}

