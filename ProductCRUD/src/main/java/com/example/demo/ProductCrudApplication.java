package com.example.demo;

import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ProductCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductCrudApplication.class, args);
	}
	
	@RequestMapping(value="/products", method=RequestMethod.GET)
	public ArrayList<String> products() {
		
		ArrayList<String> prodList = new ArrayList<String>();
		prodList.add("Apple");
		prodList.add("Samsung");
		prodList.add("Android");
		
		return prodList;
	}
	

}

