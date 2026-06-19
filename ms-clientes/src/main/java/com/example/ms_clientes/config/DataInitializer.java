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
                
                Direccion d1 = Direccion.builder().calle("Av. Siempre Viva").numero(123).ciudad("Santiago").principal(true).fechaRegistro(LocalDateTime.now()).build();
                Cliente c1 = Cliente.builder().rut("11111111-1").nombreCompleto("Juan Perez").email("juan.perez@test.cl").ingresoMensual(new BigDecimal("1000000")).activo(true).fechaNacimiento(LocalDate.of(1990, 5, 20)).build();
                d1.setCliente(c1);
                c1.setDirecciones(List.of(d1));

                Direccion d2 = Direccion.builder().calle("Calle Falsa").numero(456).ciudad("Valparaiso").principal(true).fechaRegistro(LocalDateTime.now()).build();
                Cliente c2 = Cliente.builder().rut("22222222-2").nombreCompleto("Maria Lopez").email("maria.lopez@test.cl").ingresoMensual(BigDecimal.valueOf(1500000)).activo(true).fechaNacimiento(LocalDate.of(1985, 8, 15)).build();
                d2.setCliente(c2);
                c2.setDirecciones(List.of(d2));

                Direccion d3 = Direccion.builder().calle("Avenida").numero(789).ciudad("Concepcion").principal(true).fechaRegistro(LocalDateTime.now()).build();
                Cliente c3 = Cliente.builder().rut("33333333-3").nombreCompleto("Pedro Gomez").email("pedro.gomez@test.cl").ingresoMensual(BigDecimal.valueOf(800000)).activo(false).fechaNacimiento(LocalDate.of(1995, 3, 10)).build();
                d3.setCliente(c3);
                c3.setDirecciones(List.of(d3));

                clienteRepository.saveAll(List.of(c1, c2, c3));
                log.info("Datos poblados exitosamente.");
            }
        };
    }
}
