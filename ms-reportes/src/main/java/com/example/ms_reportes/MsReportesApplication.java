package com.example.ms_reportes;

import com.example.ms_reportes.entity.Reporte;
import com.example.ms_reportes.repository.ReporteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
@EnableFeignClients
public class MsReportesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsReportesApplication.class, args);
    }

    @Bean
    public CommandLineRunner poblarDatosOBLIGATORIOS(ReporteRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Reporte(null, "Reporte Mensual de Ventas", "FINANZAS", 25, LocalDate.now().minusDays(5), true, "Análisis de ingresos por arriendo"));
                repository.save(new Reporte(null, "Disponibilidad de Vehículos", "OPERACIONES", 120, LocalDate.now().minusDays(2), true, "Flota operativa total"));
                repository.save(new Reporte(null, "Auditoría de Sucursales Activas", "ESTADISTICA", 8, LocalDate.now(), true, "Rendimiento geográfico de sedes"));

            }
        };
    }
}

