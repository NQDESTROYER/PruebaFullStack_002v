package com.example.ms_reportes.service;

import com.example.ms_reportes.client.PagoClient;
import com.example.ms_reportes.client.ReservaClient;
import com.example.ms_reportes.dto.*;
import com.example.ms_reportes.entity.Reporte;
import com.example.ms_reportes.exception.ResourceNotFoundException;
import com.example.ms_reportes.mapper.ReporteMapper;
import com.example.ms_reportes.repository.ReporteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReporteServiceImplTest {

    @Mock
    private ReporteRepository reporteRepository;

    @Mock
    private PagoClient pagoClient;

    @Mock
    private ReservaClient reservaClient;

    @Spy
    private ReporteMapper reporteMapper;

    @InjectMocks
    private ReporteServiceImpl reporteService;

    private Reporte reporteEntity;
    private ReporteRequestDTO requestDTO;
    private PagoDTO pagoDTO;
    private ReservaDTO reservaDTO;

    @BeforeEach
    void setUp() {
        reporteEntity = new Reporte();
        reporteEntity.setId(1);
        reporteEntity.setNombreReporte("Reporte Mensual");
        reporteEntity.setCategoria("Ventas");
        reporteEntity.setTotalRegistros(5);
        reporteEntity.setFechaEmision(LocalDate.now());
        reporteEntity.setProcesado(true);
        reporteEntity.setDescription("Descripción de prueba");

        requestDTO = ReporteRequestDTO.builder()
                .nombreReporte("Reporte Mensual")
                .categoria("Ventas")
                .totalRegistros(5)
                .fechaEmision(LocalDate.now())
                .description("Descripción de prueba")
                .build();

        pagoDTO = new PagoDTO();
        pagoDTO.setId(101);
        pagoDTO.setMonto(new BigDecimal("25000.00"));
        pagoDTO.setEstadoPago("APROBADO");
        pagoDTO.setCodigoTransaccion("TRX-TEST");

        reservaDTO = new ReservaDTO();
        reservaDTO.setId(201);
        reservaDTO.setClienteId(1);
        reservaDTO.setVehiculoId(2);
        reservaDTO.setEstado("CONFIRMADA");
    }

    @Test
    void generarReporteConsolidadoCompleto_Exito() {
        // Given
        when(pagoClient.obtenerTodosLosPagos()).thenReturn(Collections.singletonList(pagoDTO));
        when(reservaClient.obtenerTodasLasReservas()).thenReturn(Collections.singletonList(reservaDTO));
        when(reporteRepository.save(any(Reporte.class))).thenReturn(reporteEntity);

        // When
        ReporteConsolidadoDTO consolidado = reporteService.generarReporteConsolidadoCompleto();

        // Then
        assertNotNull(consolidado);
        assertEquals(1, consolidado.getTotalPagos());
        assertEquals(1, consolidado.getTotalReservas());
        assertEquals("Reporte consolidado unificado con éxito", consolidado.getMensaje());
        assertEquals(1, consolidado.getDetallePagos().size());
        assertEquals(1, consolidado.getDetalleReservas().size());

        verify(pagoClient, times(1)).obtenerTodosLosPagos();
        verify(reservaClient, times(1)).obtenerTodasLasReservas();
        verify(reporteRepository, times(1)).save(any(Reporte.class));
    }

    @Test
    void generarReporteConsolidadoCompleto_FalloComunicacion_LanzaExcepcion() {
        // Given
        when(pagoClient.obtenerTodosLosPagos()).thenThrow(new RuntimeException("Conexión rechazada"));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reporteService.generarReporteConsolidadoCompleto();
        });

        assertEquals("Error al conectar con los microservicios externos.", exception.getMessage());
        verify(pagoClient, times(1)).obtenerTodosLosPagos();
        verifyNoInteractions(reservaClient);
        verifyNoInteractions(reporteRepository);
    }

    @Test
    void obtenerTodos_RetornaListaDeDTOs() {
        // Given
        Reporte r2 = new Reporte(2, "Reporte Anual", "Finanzas", 10, LocalDate.now(), true, "Anual desc");
        when(reporteRepository.findAll()).thenReturn(Arrays.asList(reporteEntity, r2));

        // When
        List<ReporteResponseDTO> list = reporteService.obtenerTodos();

        // Then
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals(1, list.get(0).getId());
        assertEquals(2, list.get(1).getId());
        verify(reporteRepository, times(1)).findAll();
    }

    @Test
    void obtenerPorId_Exito_RetornaDTO() {
        // Given
        when(reporteRepository.findById(1)).thenReturn(Optional.of(reporteEntity));

        // When
        ReporteResponseDTO response = reporteService.obtenerPorId(1);

        // Then
        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals("Reporte Mensual", response.getNombreReporte());
        verify(reporteRepository, times(1)).findById(1);
    }

    @Test
    void obtenerPorId_NoExiste_LanzaExcepcion() {
        // Given
        when(reporteRepository.findById(1)).thenReturn(Optional.empty());

        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            reporteService.obtenerPorId(1);
        });

        assertEquals("Reporte no encontrado con el ID: 1", exception.getMessage());
        verify(reporteRepository, times(1)).findById(1);
    }

    @Test
    void crear_Exito_RetornaDTO() {
        // Given
        when(reporteRepository.save(any(Reporte.class))).thenReturn(reporteEntity);

        // When
        ReporteResponseDTO response = reporteService.crear(requestDTO);

        // Then
        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals("Reporte Mensual", response.getNombreReporte());
        verify(reporteRepository, times(1)).save(any(Reporte.class));
    }

    @Test
    void actualizar_Exito_RetornaDTO() {
        // Given
        when(reporteRepository.findById(1)).thenReturn(Optional.of(reporteEntity));
        when(reporteRepository.save(any(Reporte.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        ReporteResponseDTO response = reporteService.actualizar(1, requestDTO);

        // Then
        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals("Reporte Mensual", response.getNombreReporte());
        verify(reporteRepository, times(1)).findById(1);
        verify(reporteRepository, times(1)).save(any(Reporte.class));
    }

    @Test
    void actualizar_NoExiste_LanzaExcepcion() {
        // Given
        when(reporteRepository.findById(1)).thenReturn(Optional.empty());

        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            reporteService.actualizar(1, requestDTO);
        });

        assertEquals("No se puede actualizar. ID no existe: 1", exception.getMessage());
        verify(reporteRepository, times(1)).findById(1);
        verifyNoMoreInteractions(reporteRepository);
    }

    @Test
    void eliminar_Exito() {
        // Given
        when(reporteRepository.findById(1)).thenReturn(Optional.of(reporteEntity));

        // When
        reporteService.eliminar(1);

        // Then
        verify(reporteRepository, times(1)).findById(1);
        verify(reporteRepository, times(1)).delete(reporteEntity);
    }

    @Test
    void eliminar_NoExiste_LanzaExcepcion() {
        // Given
        when(reporteRepository.findById(1)).thenReturn(Optional.empty());

        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            reporteService.eliminar(1);
        });

        assertEquals("No se puede eliminar. ID no existe: 1", exception.getMessage());
        verify(reporteRepository, times(1)).findById(1);
        verifyNoMoreInteractions(reporteRepository);
    }
}
