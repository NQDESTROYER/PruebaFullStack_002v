package com.example.ms_empleados.config;

import com.example.ms_empleados.model.Empleado;
import com.example.ms_empleados.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
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
            // Verifica si la tabla está vacía antes de insertar
            if (empleadoRepository.count() == 0) {
                log.info("Iniciando la carga de los 5 registros iniciales en ms-empleados...");

                Empleado e1 = Empleado.builder()
                        .rut("11111111-1").nombreCompleto("Juan Perez").cargo("Desarrollador")
                        .sueldoBase(new BigDecimal("1500000")).conContratoIndefinido(true)
                        .fechaContratacion(LocalDate.of(2023, 5, 10)).activo(true).build();

                Empleado e2 = Empleado.builder()
                        .rut("22222222-2").nombreCompleto("Ana Gomez").cargo("Analista QA")
                        .sueldoBase(new BigDecimal("1200000")).conContratoIndefinido(true)
                        .fechaContratacion(LocalDate.of(2024, 2, 1)).activo(true).build();

                Empleado e3 = Empleado.builder()
                        .rut("33333333-3").nombreCompleto("Luis Rojas").cargo("Soporte IT")
                        .sueldoBase(new BigDecimal("900000")).conContratoIndefinido(false)
                        .fechaContratacion(LocalDate.of(2024, 1, 15)).activo(false).build();

                Empleado e4 = Empleado.builder()
                        .rut("44444444-4").nombreCompleto("Maria Silva").cargo("Scrum Master")
                        .sueldoBase(new BigDecimal("1800000")).conContratoIndefinido(true)
                        .fechaContratacion(LocalDate.of(2022, 11, 20)).activo(true).build();

                Empleado e5 = Empleado.builder()
                        .rut("55555555-5").nombreCompleto("Pedro Soto").cargo("DevOps")
                        .sueldoBase(new BigDecimal("1700000")).conContratoIndefinido(false)
                        .fechaContratacion(LocalDate.of(2024, 3, 10)).activo(true).build();

                empleadoRepository.saveAll(List.of(e1, e2, e3, e4, e5));
                log.info("¡Los 5 empleados se cargaron exitosamente en la base de datos!");
            } else {
                log.info("La base de datos ya contiene registros, omitiendo carga inicial.");
            }
        };
    }
}