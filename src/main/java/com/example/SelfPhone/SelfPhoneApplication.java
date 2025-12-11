package com.example.SelfPhone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SelfPhoneApplication {

	public static void main(String[] args) {
		SpringApplication.run(SelfPhoneApplication.class, args);
	}

}
