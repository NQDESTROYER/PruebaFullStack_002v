package com.example.eureka_serves;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServesApplication {

	public static void main(String[] args) {

		SpringApplication.run(EurekaServesApplication.class, args);
	}

}
