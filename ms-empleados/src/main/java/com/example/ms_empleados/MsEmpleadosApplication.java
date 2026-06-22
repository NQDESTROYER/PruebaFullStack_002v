package com.example.ms_empleados;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsEmpleadosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsEmpleadosApplication.class, args);
	}

}
