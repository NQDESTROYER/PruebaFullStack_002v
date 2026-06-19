package com.example.ms_empleados.config;

import com.example.ms_empleados.model.Empleado;
import com.example.ms_empleados.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {

    private final EmpleadoRepository empleadoRepository;

    @Bean
    CommandLineRunner initData() {
        return args -> {
            if (empleadoRepository.count() == 0) {
                log.info("Poblando datos iniciales en ms-empleados...");
                Empleado e1 = Empleado.builder().nombreCompleto("Juan").conContratoIndefinido(true).fechaContratacion(LocalDate.of(2025, 1, 1)).build();
                Empleado e2 = Empleado.builder().nombreCompleto("Ana").conContratoIndefinido(true).fechaContratacion(LocalDate.of(2025, 2, 1)).build();
                Empleado e3 = Empleado.builder().nombreCompleto("Luis").conContratoIndefinido(false).fechaContratacion(LocalDate.of(2024, 1, 1)).build();
                empleadoRepository.saveAll(List.of(e1, e2, e3));
                log.info("Datos poblados exitosamente.");
            }
        };
    }
}
