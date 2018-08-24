package com.alta.hello;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class HelloApplication {

	public static void main(String[] args) {
		log.info("the service to start.");
		System.out.println("The service to start.");
		SpringApplication.run(HelloApplication.class, args);
		System.out.println("The service has started.");
		log.info("the service has started.");
	}
}
