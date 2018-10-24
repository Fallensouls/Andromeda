package com.fallensouls.messageservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MessageServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessageServiceApplication.class, args);
	}
}
