package com.example.ms_reportes.client;

import com.example.ms_reportes.dto.PagoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@FeignClient(name = "ms-pagos", url = "http://localhost:8084/api/v1/pagos")
public interface PagoClient {

    @GetMapping
    List<PagoDTO> obtenerTodosLosPagos();
}
