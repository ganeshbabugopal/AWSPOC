package com.example.demo;

import java.util.LinkedHashMap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
public class OrderCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderCrudApplication.class, args);
	}
	
	@RequestMapping(value="/quote", method=RequestMethod.GET)
	public String createOrder() {
		
		return "CRUD - Order created ";
	}
	
	@RequestMapping(value="/productorder", method=RequestMethod.GET)
	public String productOrder() {
		
		RestTemplate rt = new RestTemplate();
		
		String map = rt.getForObject("http://productcrud.default/products", String.class);
		return "Removed port and Updated with namespace and local cluster with citi Product List " + map;
	}
	

}

