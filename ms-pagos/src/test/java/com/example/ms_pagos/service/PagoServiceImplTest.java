package com.example.ms_pagos.service;

import com.example.ms_pagos.client.ReservaClient;
import com.example.ms_pagos.dto.PagoRequestDTO;
import com.example.ms_pagos.dto.PagoResponseDTO;
import com.example.ms_pagos.dto.ReservaDTO;
import com.example.ms_pagos.exception.ResourceNotFoundException;
import com.example.ms_pagos.mapper.PagoMapper;
import com.example.ms_pagos.model.MetodoPago;
import com.example.ms_pagos.model.Pago;
import com.example.ms_pagos.repository.MetodoPagoRepository;
import com.example.ms_pagos.repository.PagoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PagoServiceImplTest {

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private MetodoPagoRepository metodoPagoRepository;

    @Mock
    private ReservaClient reservaClient;

    @Spy
    private PagoMapper pagoMapper;

    @InjectMocks
    private PagoServiceImpl pagoService;

    private MetodoPago metodoPago;
    private PagoRequestDTO requestDTO;
    private ReservaDTO reservaDTO;
    private Pago pagoEntity;

    @BeforeEach
    void setUp() {
        metodoPago = new MetodoPago(1, "Tarjeta", "Pago con tarjeta", true);
        
        requestDTO = PagoRequestDTO.builder()
                .reservaId(10)
                .monto(new BigDecimal("15000.00"))
                .metodoPagoId(1)
                .build();

        reservaDTO = new ReservaDTO();
        reservaDTO.setId(10);
        reservaDTO.setClienteId(5);
        reservaDTO.setVehiculoId(8);
        reservaDTO.setEstado("PENDIENTE");

        pagoEntity = new Pago();
        pagoEntity.setId(1);
        pagoEntity.setReservaId(10);
        pagoEntity.setMonto(new BigDecimal("15000.00"));
        pagoEntity.setMetodoPago(metodoPago);
        pagoEntity.setCodigoTransaccion("TRX-ABC12345");
        pagoEntity.setFechaPago(LocalDateTime.now());
        pagoEntity.setEstadoPago("APROBADO");
    }

    @Test
    void registrarPago_Exito() {
        // Given
        when(metodoPagoRepository.findById(1)).thenReturn(Optional.of(metodoPago));
        when(reservaClient.obtenerReservaPorId(10)).thenReturn(reservaDTO);
        when(pagoRepository.save(any(Pago.class))).thenAnswer(invocation -> {
            Pago p = invocation.getArgument(0);
            p.setId(1);
            return p;
        });

        // When
        PagoResponseDTO response = pagoService.registrarPago(requestDTO);

        // Then
        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals(10, response.getReservaId());
        assertEquals(new BigDecimal("15000.00"), response.getMonto());
        assertNotNull(response.getCodigoTransaccion());
        assertTrue(response.getCodigoTransaccion().startsWith("TRX-"));
        assertEquals("APROBADO", response.getEstadoPago());
        assertEquals(1, response.getMetodoPagoId());
        assertEquals("Tarjeta", response.getMetodoPagoNombre());

        verify(metodoPagoRepository, times(1)).findById(1);
        verify(reservaClient, times(1)).obtenerReservaPorId(10);
        verify(pagoRepository, times(1)).save(any(Pago.class));
    }

    @Test
    void registrarPago_MetodoPagoNoExiste_LanzaExcepcion() {
        // Given
        when(metodoPagoRepository.findById(1)).thenReturn(Optional.empty());

        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            pagoService.registrarPago(requestDTO);
        });

        assertEquals("Método de pago no encontrado con ID: 1", exception.getMessage());
        verify(metodoPagoRepository, times(1)).findById(1);
        verifyNoInteractions(reservaClient);
        verifyNoInteractions(pagoRepository);
    }

    @Test
    void registrarPago_ReservaNoExiste_LanzaExcepcion() {
        // Given
        when(metodoPagoRepository.findById(1)).thenReturn(Optional.of(metodoPago));
        when(reservaClient.obtenerReservaPorId(10)).thenReturn(null);

        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            pagoService.registrarPago(requestDTO);
        });

        assertEquals("La reserva con ID 10 no existe.", exception.getMessage());
        verify(metodoPagoRepository, times(1)).findById(1);
        verify(reservaClient, times(1)).obtenerReservaPorId(10);
        verifyNoInteractions(pagoRepository);
    }

    @Test
    void obtenerTodos_RetornaListaDeDTOs() {
        // Given
        Pago pago2 = new Pago();
        pago2.setId(2);
        pago2.setReservaId(11);
        pago2.setMonto(new BigDecimal("20000.00"));
        pago2.setMetodoPago(metodoPago);
        pago2.setCodigoTransaccion("TRX-XYZ98765");
        pago2.setFechaPago(LocalDateTime.now());
        pago2.setEstadoPago("APROBADO");

        when(pagoRepository.findAll()).thenReturn(Arrays.asList(pagoEntity, pago2));

        // When
        List<PagoResponseDTO> list = pagoService.obtenerTodos();

        // Then
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals(1, list.get(0).getId());
        assertEquals(2, list.get(1).getId());
        verify(pagoRepository, times(1)).findAll();
    }

    @Test
    void obtenerPorId_Exito_RetornaDTO() {
        // Given
        when(pagoRepository.findById(1)).thenReturn(Optional.of(pagoEntity));

        // When
        PagoResponseDTO response = pagoService.obtenerPorId(1);

        // Then
        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals("TRX-ABC12345", response.getCodigoTransaccion());
        verify(pagoRepository, times(1)).findById(1);
    }

    @Test
    void obtenerPorId_NoExiste_LanzaExcepcion() {
        // Given
        when(pagoRepository.findById(1)).thenReturn(Optional.empty());

        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            pagoService.obtenerPorId(1);
        });

        assertEquals("Pago no encontrado con ID: 1", exception.getMessage());
        verify(pagoRepository, times(1)).findById(1);
    }
}
