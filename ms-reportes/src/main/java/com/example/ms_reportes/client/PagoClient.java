package com.example.ms_reportes.client;

import com.example.ms_reportes.dto.PagoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@FeignClient(name = "ms-pagos", path = "/api/pagos")
public interface PagoClient {

    @GetMapping
    List<PagoDTO> obtenerTodosLosPagos();
}