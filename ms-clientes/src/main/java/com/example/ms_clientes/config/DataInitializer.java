package com.example.ms_clientes.config;

import com.example.ms_clientes.model.Cliente;
import com.example.ms_clientes.model.Direccion;
import com.example.ms_clientes.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {

    private final ClienteRepository clienteRepository;

    @Bean
    CommandLineRunner initData() {
        return args -> {
            if (clienteRepository.count() == 0) {
                log.info("Poblando datos iniciales en ms-clientes...");

                // --- Cliente 1 ---
                Cliente c1 = Cliente.builder().rut("11111111-1").nombreCompleto("Juan Perez").email("juan.perez@test.cl").ingresoMensual(new BigDecimal("1000000")).activo(true).fechaNacimiento(LocalDate.of(1990, 5, 20)).build();
                c1.addDireccion(Direccion.builder().calle("Av. Siempre Viva").numero(123).ciudad("Santiago").principal(true).fechaRegistro(LocalDateTime.now()).build());

                // --- Cliente 2 ---
                Cliente c2 = Cliente.builder().rut("22222222-2").nombreCompleto("Maria Lopez").email("maria.lopez@test.cl").ingresoMensual(new BigDecimal("1500000")).activo(true).fechaNacimiento(LocalDate.of(1985, 8, 15)).build();
                c2.addDireccion(Direccion.builder().calle("Calle Falsa").numero(456).ciudad("Valparaiso").principal(true).fechaRegistro(LocalDateTime.now()).build());

                // --- Cliente 3 (Ajustado) ---
                Cliente c3 = Cliente.builder().rut("33333333-3").nombreCompleto("Pedro Gomez").email("pedro.gomez@test.cl").ingresoMensual(new BigDecimal("800000")).activo(false).fechaNacimiento(LocalDate.of(1995, 3, 10)).build();
                c3.addDireccion(Direccion.builder().calle("Avenida Bernardo O'Higgins").numero(789).ciudad("Concepcion").principal(true).fechaRegistro(LocalDateTime.now()).build());

                // --- Cliente 4 ---
                Cliente c4 = Cliente.builder().rut("44444444-4").nombreCompleto("Ana Silva").email("ana.silva@test.cl").ingresoMensual(new BigDecimal("2200000")).activo(true).fechaNacimiento(LocalDate.of(1988, 11, 5)).build();
                c4.addDireccion(Direccion.builder().calle("Los Leones").numero(101).ciudad("Santiago").principal(true).fechaRegistro(LocalDateTime.now()).build());

                // --- Cliente 5 (Corregido: "Arturo Prat" pasa la validación de @Size min=5) ---
                Cliente c5 = Cliente.builder().rut("55555555-5").nombreCompleto("Luis Rojas").email("luis.rojas@test.cl").ingresoMensual(new BigDecimal("1200000")).activo(true).fechaNacimiento(LocalDate.of(1992, 1, 25)).build();
                c5.addDireccion(Direccion.builder().calle("Calle Arturo Prat").numero(202).ciudad("Antofagasta").principal(true).fechaRegistro(LocalDateTime.now()).build());

                // Guardamos los 5 clientes. Hibernate guarda las direcciones automáticamente en cascada.
                clienteRepository.saveAll(List.of(c1, c2, c3, c4, c5));

                log.info(" Datos poblados exitosamente. Total de registros: 5 clientes y sus direcciones.");
            } else {
                log.info("La base de datos ya contiene registros. Omitiendo carga inicial.");
            }
        };
    }
}