package com.example.ms_reservas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Apunta al microservicio de tu compañero (ms-clientes) en el puerto 8081
@FeignClient(name = "ms-clientes", url = "http://localhost:8081/api/v1/clientes")
public interface ClienteClient {

    @GetMapping("/{id}")
    Object obtenerClientePorId(@PathVariable("id") Integer id);
}