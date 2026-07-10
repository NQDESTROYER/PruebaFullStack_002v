package com.example.ms_reservas.service;

import com.example.ms_reservas.client.ClienteClient;
import com.example.ms_reservas.client.VehiculoClient;
import com.example.ms_reservas.dto.ReservaRequestDTO;
import com.example.ms_reservas.dto.ReservaResponseDTO;
import com.example.ms_reservas.exception.ResourceNotFoundException;
import com.example.ms_reservas.mapper.ReservaMapper;
import com.example.ms_reservas.model.EstadoReserva;
import com.example.ms_reservas.model.Reserva;
import com.example.ms_reservas.repository.EstadoReservaRepository;
import com.example.ms_reservas.repository.ReservaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservaServiceImplTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private EstadoReservaRepository estadoRepository;

    @Mock
    private ClienteClient clienteClient;

    @Mock
    private VehiculoClient vehiculoClient;

    @Spy
    private ReservaMapper reservaMapper;

    @InjectMocks
    private ReservaServiceImpl reservaService;

    private EstadoReserva estado;
    private ReservaRequestDTO requestDTO;
    private Reserva reservaEntity;

    @BeforeEach
    void setUp() {
        estado = new EstadoReserva();
        estado.setId(1);
        estado.setNombreEstado("CONFIRMADA");
        estado.setDescripcion("Reserva confirmada");
        estado.setPermiteModificacion(true);
        estado.setNivelPrioridad(1);
        estado.setFechaCreacion(LocalDate.now());

        requestDTO = new ReservaRequestDTO();
        requestDTO.setCodigoReserva("RES-12345");
        requestDTO.setFechaInicio(LocalDate.now());
        requestDTO.setFechaFin(LocalDate.now().plusDays(5));
        requestDTO.setMontoTotal(java.math.BigDecimal.valueOf(50000.0));
        requestDTO.setSeguroIncluido(true);
        requestDTO.setClienteId(10);
        requestDTO.setVehiculoId(20);
        requestDTO.setEstadoId(1);

        reservaEntity = new Reserva();
        reservaEntity.setId(100);
        reservaEntity.setCodigoReserva("RES-12345");
        reservaEntity.setFechaInicio(LocalDate.now());
        reservaEntity.setFechaFin(LocalDate.now().plusDays(5));
        reservaEntity.setMontoTotal(java.math.BigDecimal.valueOf(50000.0));
        reservaEntity.setSeguroIncluido(true);
        reservaEntity.setClienteId(10);
        reservaEntity.setVehiculoId(20);
        reservaEntity.setEstado(estado);
    }

    @Test
    void listarTodas_Exito() {
        when(reservaRepository.findAll()).thenReturn(Collections.singletonList(reservaEntity));

        List<ReservaResponseDTO> result = reservaService.listarTodas();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("RES-12345", result.get(0).getCodigoReserva());
        verify(reservaRepository, times(1)).findAll();
    }

    @Test
    void buscarDesdeFecha_Exito() {
        LocalDate now = LocalDate.now();
        when(reservaRepository.buscarDesdeFecha(now)).thenReturn(Collections.singletonList(reservaEntity));

        List<ReservaResponseDTO> result = reservaService.buscarDesdeFecha(now);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(reservaRepository, times(1)).buscarDesdeFecha(now);
    }

    @Test
    void obtenerPorId_Exito() {
        when(reservaRepository.findById(100)).thenReturn(Optional.of(reservaEntity));

        ReservaResponseDTO result = reservaService.obtenerPorId(100);

        assertNotNull(result);
        assertEquals(100, result.getId());
        assertEquals("RES-12345", result.getCodigoReserva());
        verify(reservaRepository, times(1)).findById(100);
    }

    @Test
    void obtenerPorId_NoExiste_LanzaExcepcion() {
        when(reservaRepository.findById(100)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> reservaService.obtenerPorId(100));

        verify(reservaRepository, times(1)).findById(100);
    }

    @Test
    void crear_Exito() {
        // Devolver null suele ser suficiente si el servicio solo comprueba que no arroje error 404.
        when(clienteClient.obtenerClientePorId(10)).thenReturn(null);
        when(vehiculoClient.obtenerVehiculoPorId(20)).thenReturn(null);
        when(estadoRepository.findById(1)).thenReturn(Optional.of(estado));
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reservaEntity);

        ReservaResponseDTO result = reservaService.crear(requestDTO);

        assertNotNull(result);
        assertEquals("RES-12345", result.getCodigoReserva());
        verify(clienteClient, times(1)).obtenerClientePorId(10);
        verify(vehiculoClient, times(1)).obtenerVehiculoPorId(20);
        verify(estadoRepository, times(1)).findById(1);
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }

    @Test
    void crear_ClienteNoExiste_LanzaExcepcion() {
        when(clienteClient.obtenerClientePorId(10)).thenThrow(new RuntimeException("Cliente no encontrado"));

        assertThrows(ResourceNotFoundException.class, () -> reservaService.crear(requestDTO));

        verify(clienteClient, times(1)).obtenerClientePorId(10);
        verifyNoInteractions(vehiculoClient);
        verifyNoInteractions(estadoRepository);
        verifyNoInteractions(reservaRepository);
    }

    @Test
    void crear_VehiculoNoExiste_LanzaExcepcion() {
        when(clienteClient.obtenerClientePorId(10)).thenReturn(null);
        when(vehiculoClient.obtenerVehiculoPorId(20)).thenThrow(new RuntimeException("Vehiculo no encontrado"));

        assertThrows(ResourceNotFoundException.class, () -> reservaService.crear(requestDTO));

        verify(clienteClient, times(1)).obtenerClientePorId(10);
        verify(vehiculoClient, times(1)).obtenerVehiculoPorId(20);
        verifyNoInteractions(estadoRepository);
        verifyNoInteractions(reservaRepository);
    }

    @Test
    void crear_EstadoNoExiste_LanzaExcepcion() {
        when(clienteClient.obtenerClientePorId(10)).thenReturn(null);
        when(vehiculoClient.obtenerVehiculoPorId(20)).thenReturn(null);
        when(estadoRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> reservaService.crear(requestDTO));

        verify(clienteClient, times(1)).obtenerClientePorId(10);
        verify(vehiculoClient, times(1)).obtenerVehiculoPorId(20);
        verify(estadoRepository, times(1)).findById(1);
        verifyNoInteractions(reservaRepository);
    }

    @Test
    void actualizar_Exito() {
        when(reservaRepository.findById(100)).thenReturn(Optional.of(reservaEntity));
        when(estadoRepository.findById(1)).thenReturn(Optional.of(estado));
        when(reservaRepository.save(any(Reserva.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ReservaResponseDTO result = reservaService.actualizar(100, requestDTO);

        assertNotNull(result);
        assertEquals("RES-12345", result.getCodigoReserva());
        verify(reservaRepository, times(1)).findById(100);
        verify(estadoRepository, times(1)).findById(1);
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }

    @Test
    void actualizar_NoExiste_LanzaExcepcion() {
        when(reservaRepository.findById(100)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> reservaService.actualizar(100, requestDTO));

        verify(reservaRepository, times(1)).findById(100);
        verifyNoInteractions(estadoRepository);
    }

    @Test
    void eliminar_Exito() {
        when(reservaRepository.existsById(100)).thenReturn(true);
        doNothing().when(reservaRepository).deleteById(100);

        reservaService.eliminar(100);

        verify(reservaRepository, times(1)).existsById(100);
        verify(reservaRepository, times(1)).deleteById(100);
    }

    @Test
    void eliminar_NoExiste_LanzaExcepcion() {
        when(reservaRepository.existsById(100)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> reservaService.eliminar(100));

        verify(reservaRepository, times(1)).existsById(100);
        verify(reservaRepository, never()).deleteById(anyInt());
    }
}