package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;


import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;
@SpringBootApplication
@ComponentScan("com.example.demo")
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class AiPoweredPersonalFinanceTracker1Application {

	public static void main(String[] args) {
		
		SpringApplication.run(AiPoweredPersonalFinanceTracker1Application.class, args);
		
	}
	

//	@Autowired
//    private PasswordEncoder encoder;
//	
//	
//	// i have used this for password generation for admin...remember in every machiine different code is generated
//	@PostConstruct
//	public void generatePassword() {
//	    System.out.println("ENCODED = " + encoder.encode("dev123"));
//	}
}


