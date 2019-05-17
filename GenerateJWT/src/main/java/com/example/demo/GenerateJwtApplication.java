package com.example.demo;

import java.security.NoSuchAlgorithmException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;

@SpringBootApplication
@RestController
public class GenerateJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(GenerateJwtApplication.class, args);
	}
	
@RequestMapping(value="/generateToken",method=RequestMethod.GET)
	public String tokenGenerator() throws NoSuchAlgorithmException, JOSEException {
	return TokenGenerator.generateToken();
}

}
