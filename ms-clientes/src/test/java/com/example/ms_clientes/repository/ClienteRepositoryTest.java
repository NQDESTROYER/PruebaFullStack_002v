package com.example.ms_clientes.repository;

import com.example.ms_clientes.model.Cliente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void testFindByEmailContainingIgnoreCase() {
        // Arrange
        Cliente c1 = Cliente.builder()
                .rut("11111111-1")
                .nombreCompleto("Juan Perez")
                .email("juan.PEREZ@gmail.com")
                .ingresoMensual(BigDecimal.valueOf(1000000))
                .activo(true)
                .fechaNacimiento(LocalDate.of(1990, 1, 1))
                .build();

        Cliente c2 = Cliente.builder()
                .rut("22222222-2")
                .nombreCompleto("Maria Lopez")
                .email("maria@hotmail.com")
                .ingresoMensual(BigDecimal.valueOf(800000))
                .activo(true)
                .fechaNacimiento(LocalDate.of(1995, 5, 5))
                .build();

        clienteRepository.save(c1);
        clienteRepository.save(c2);

        // Act
        List<Cliente> resultado = clienteRepository.findByEmailContainingIgnoreCase("perez@GMAIL");

        // Assert
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNombreCompleto()).isEqualTo("Juan Perez");
    }
}