package com.example.ms_empleados.config;

import com.example.ms_empleados.entity.Empleado;
import com.example.ms_empleados.repository.EmpleadoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {
    private final EmpleadoRepository empleadoRepository;

    public DataInitializer(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (empleadoRepository.count() == 0) {

            // Corregido: Usamos BigDecimal.valueOf() para los sueldos y Boolean.TRUE/FALSE
            Empleado e1 = new Empleado(null, "12345678-9", "Juan Pérez", "Mecánico Senior", BigDecimal.valueOf(850000), Boolean.TRUE, LocalDate.of(2024, 3, 15));
            Empleado e2 = new Empleado(null, "18765432-1", "María Ortega", "Ejecutiva de Ventas", BigDecimal.valueOf(650000), Boolean.TRUE, LocalDate.of(2025, 1, 10));
            Empleado e3 = new Empleado(null, "15432987-K", "Carlos Soto", "Supervisor de Flota", BigDecimal.valueOf(1100000), Boolean.FALSE, LocalDate.of(2023, 6, 20));

            empleadoRepository.save(e1);
            empleadoRepository.save(e2);
            empleadoRepository.save(e3);

            System.out.println("=============================================");
            System.out.println("=== EMPLEADOS INICIALES CARGADOS EN LARAGON ===");
            System.out.println("=============================================");
        }
    }
}
