package com.example.ms_empleados.service;

import com.example.ms_empleados.dto.EmpleadoRequestDTO;
import com.example.ms_empleados.dto.EmpleadoResponseDTO;
import com.example.ms_empleados.mapper.EmpleadoMapper;
import com.example.ms_empleados.model.Empleado;
import com.example.ms_empleados.repository.EmpleadoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmpleadoServiceImplTest {

    @Mock
    private EmpleadoRepository repository;

    @Mock
    private EmpleadoMapper mapper;

    @InjectMocks
    private EmpleadoServiceImpl service;

    @Test
    @DisplayName("Debe crear un empleado usando DTOs y Mapper")
    void crearEmpleado_Exitoso() {
        EmpleadoRequestDTO requestDTO = new EmpleadoRequestDTO();
        requestDTO.setRut("11111111-1");

        Empleado entidadMock = Empleado.builder().rut("11111111-1").build();
        Empleado entidadGuardada = Empleado.builder().id(1).rut("11111111-1").build();
        EmpleadoResponseDTO responseDTO = EmpleadoResponseDTO.builder().id(1).rut("11111111-1").build();

        when(mapper.toEntity(any(EmpleadoRequestDTO.class))).thenReturn(entidadMock);
        when(repository.save(any(Empleado.class))).thenReturn(entidadGuardada);
        when(mapper.toResponseDTO(any(Empleado.class))).thenReturn(responseDTO);

        EmpleadoResponseDTO resultado = service.crear(requestDTO);

        assertNotNull(resultado.getId());
        verify(repository).save(any(Empleado.class));
    }

    @Test
    @DisplayName("Debe retornar un empleado por ID")
    void buscarPorId_Exitoso() {
        Empleado entidadMock = Empleado.builder().id(1).nombreCompleto("Maria").build();
        EmpleadoResponseDTO responseDTO = EmpleadoResponseDTO.builder().id(1).nombreCompleto("Maria").build();

        when(repository.findById(1)).thenReturn(Optional.of(entidadMock));
        when(mapper.toResponseDTO(entidadMock)).thenReturn(responseDTO);

        EmpleadoResponseDTO resultado = service.buscarPorId(1);

        assertEquals("Maria", resultado.getNombreCompleto());
        verify(repository).findById(1);
    }
}