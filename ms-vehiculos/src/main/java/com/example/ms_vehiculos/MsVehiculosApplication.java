package com.example.ms_vehiculos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = "com.example.ms_vehiculos")
@EnableDiscoveryClient
public class MsVehiculosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsVehiculosApplication.class, args);
	}

}
