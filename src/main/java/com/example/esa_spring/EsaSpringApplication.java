package com.example.esa_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;

@SpringBootApplication
public class EsaSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(EsaSpringApplication.class, args);
	}

}
