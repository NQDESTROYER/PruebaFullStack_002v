package com.example.ms_reservas.service;

import com.example.ms_reservas.dto.EstadoReservaRequestDTO;
import com.example.ms_reservas.dto.EstadoReservaResponseDTO;
import com.example.ms_reservas.exception.ResourceNotFoundException;
import com.example.ms_reservas.mapper.EstadoReservaMapper;
import com.example.ms_reservas.model.EstadoReserva;
import com.example.ms_reservas.repository.EstadoReservaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EstadoReservaServiceImplTest {

    @Mock
    private EstadoReservaRepository repository;

    @Spy
    private EstadoReservaMapper mapper;

    @InjectMocks
    private EstadoReservaServiceImpl service;

    private EstadoReserva estadoEntity;
    private EstadoReservaRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        estadoEntity = new EstadoReserva();
        estadoEntity.setId(1);
        estadoEntity.setNombreEstado("CONFIRMADA");
        estadoEntity.setDescripcion("Reserva confirmada");
        estadoEntity.setPermiteModificacion(true);
        estadoEntity.setNivelPrioridad(1);
        estadoEntity.setFechaCreacion(LocalDate.now());

        requestDTO = new EstadoReservaRequestDTO();
        requestDTO.setNombreEstado("CONFIRMADA");
        requestDTO.setDescripcion("Reserva confirmada");
        requestDTO.setPermiteModificacion(true);
        requestDTO.setNivelPrioridad(1);
        requestDTO.setFechaCreacion(LocalDate.now());
    }

    @Test
    void listarTodos_Exito() {
        when(repository.findAll()).thenReturn(Arrays.asList(estadoEntity));

        List<EstadoReservaResponseDTO> result = service.listarTodos();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("CONFIRMADA", result.get(0).getNombreEstado());
        verify(repository, times(1)).findAll();
    }

    @Test
    void obtenerPorId_Exito() {
        when(repository.findById(1)).thenReturn(Optional.of(estadoEntity));

        EstadoReservaResponseDTO result = service.obtenerPorId(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("CONFIRMADA", result.getNombreEstado());
        verify(repository, times(1)).findById(1);
    }

    @Test
    void obtenerPorId_NoExiste_LanzaExcepcion() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            service.obtenerPorId(1);
        });

        verify(repository, times(1)).findById(1);
    }

    @Test
    void crear_Exito() {
        when(repository.save(any(EstadoReserva.class))).thenReturn(estadoEntity);

        EstadoReservaResponseDTO result = service.crear(requestDTO);

        assertNotNull(result);
        assertEquals("CONFIRMADA", result.getNombreEstado());
        verify(repository, times(1)).save(any(EstadoReserva.class));
    }

    @Test
    void actualizar_Exito() {
        when(repository.findById(1)).thenReturn(Optional.of(estadoEntity));
        when(repository.save(any(EstadoReserva.class))).thenAnswer(invocation -> invocation.getArgument(0));

        EstadoReservaResponseDTO result = service.actualizar(1, requestDTO);

        assertNotNull(result);
        assertEquals("CONFIRMADA", result.getNombreEstado());
        verify(repository, times(1)).findById(1);
        verify(repository, times(1)).save(any(EstadoReserva.class));
    }

    @Test
    void actualizar_NoExiste_LanzaExcepcion() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            service.actualizar(1, requestDTO);
        });

        verify(repository, times(1)).findById(1);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void eliminar_Exito() {
        when(repository.existsById(1)).thenReturn(true);
        doNothing().when(repository).deleteById(1);

        service.eliminar(1);

        verify(repository, times(1)).existsById(1);
        verify(repository, times(1)).deleteById(1);
    }

    @Test
    void eliminar_NoExiste_LanzaExcepcion() {
        when(repository.existsById(1)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            service.eliminar(1);
        });

        verify(repository, times(1)).existsById(1);
        verify(repository, never()).deleteById(anyInt());
    }
}
