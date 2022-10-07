package com.computer.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SingleprojApplication {

	public static void main(String[] args) {
		SpringApplication.run(SingleprojApplication.class, args);
	}

}
