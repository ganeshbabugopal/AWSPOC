package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class AuthenticatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticatorApplication.class, args);
	}
	
	
	@RequestMapping(value="/signon", method=RequestMethod.GET)
	public String signon(@RequestParam String userId, @RequestParam String password) {
		System.out.println(" in signon request");
		
		
		if (userId.equals("ganesh"))
		{
			return "success";
		}
		
		return "failure";
	}

}
