package com.example.ms_reservas.repository;

import com.example.ms_reservas.model.EstadoReserva;
import com.example.ms_reservas.model.Reserva;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test") // Usa tu archivo application-test.properties con H2
class ReservaRepositoryTest {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private EstadoReservaRepository estadoReservaRepository;

    @Test
    void buscarDesdeFecha_DebeRetornarReservasFiltadasCorrectamente() {
        // Arrange: Preparamos un estado y una reserva de prueba en la BD H2 en memoria
        EstadoReserva estado = new EstadoReserva();
        estado.setNombreEstado("CONFIRMADA");
        estado.setDescripcion("Reserva Confirmada");
        estado.setPermiteModificacion(false);
        estado.setNivelPrioridad(1);
        estado.setFechaCreacion(LocalDate.now());
        estado = estadoReservaRepository.save(estado);

        Reserva reserva = new Reserva();
        reserva.setCodigoReserva("RES-TEST-REP");
        reserva.setFechaInicio(LocalDate.now().plusDays(2));
        reserva.setFechaFin(LocalDate.now().plusDays(5));
        reserva.setClienteId(1);
        reserva.setVehiculoId(1);
        reserva.setEstado(estado);
        reserva.setMontoTotal(new BigDecimal("120000.00"));
        reserva.setSeguroIncluido(true);
        reservaRepository.save(reserva);

        // Act: Ejecutamos el método personalizado de tu repositorio
        List<Reserva> result = reservaRepository.buscarDesdeFecha(LocalDate.now());

        // Assert: Validamos que devuelva la data y coincida el código
        assertFalse(result.isEmpty(), "La lista de reservas no debería estar vacía");
        assertEquals("RES-TEST-REP", result.get(0).getCodigoReserva());
    }
}