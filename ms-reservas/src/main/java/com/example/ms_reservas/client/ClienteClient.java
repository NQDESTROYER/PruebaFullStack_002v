package com.example.ms_reservas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Cliente Feign para el microservicio ms-clientes
@FeignClient(name = "ms-clientes", path = "/api/clientes")
public interface ClienteClient {

    @GetMapping("/{id}")
    Object obtenerClientePorId(@PathVariable("id") Integer id);
}