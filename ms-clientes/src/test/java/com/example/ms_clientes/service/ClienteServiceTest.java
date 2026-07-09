package com.example.ms_clientes.service;

import com.example.ms_clientes.dto.ClienteResponseDTO;
import com.example.ms_clientes.dto.ClientesRequestDTO;
import com.example.ms_clientes.exception.ResourceNotFoundException; // Tu excepción personalizada
import com.example.ms_clientes.mapper.ClienteMapper;
import com.example.ms_clientes.model.Cliente;
import com.example.ms_clientes.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteMapper clienteMapper;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private Cliente clienteMock;
    private ClientesRequestDTO requestMock;
    private ClienteResponseDTO responseMock;

    @BeforeEach
    void setUp() {
        clienteMock = Cliente.builder()
                .id(1)
                .rut("11111111-1")
                .nombreCompleto("Juan Pérez Test")
                .email("juan@test.cl")
                .ingresoMensual(new BigDecimal("1000000"))
                .activo(true)
                .fechaNacimiento(LocalDate.of(1990, 1, 1))
                .direcciones(new ArrayList<>())
                .build();

        requestMock = ClientesRequestDTO.builder()
                .rut("11111111-1")
                .nombreCompleto("Juan Pérez Test")
                .email("juan@test.cl")
                .ingresoMensual(new BigDecimal("1000000"))
                .activo(true)
                .fechaNacimiento(LocalDate.of(1990, 1, 1))
                .build();

        responseMock = ClienteResponseDTO.builder()
                .id(1)
                .rut("11111111-1")
                .nombreCompleto("Juan Pérez Test")
                .build();
    }

    @Test
    void testCrear_Exito() {
        // Arrange
        when(clienteMapper.toEntity(any(ClientesRequestDTO.class))).thenReturn(clienteMock);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteMock);
        when(clienteMapper.toResponseDTO(any(Cliente.class))).thenReturn(responseMock);

        // Act - Llama a tu método 'crear'
        ClienteResponseDTO resultado = clienteService.crear(requestMock);

        // Assert
        assertNotNull(resultado);
        assertEquals("11111111-1", resultado.getRut());
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void testBuscarPorId_Exito() {
        // Arrange
        when(clienteRepository.findById(1)).thenReturn(Optional.of(clienteMock));
        when(clienteMapper.toResponseDTO(any(Cliente.class))).thenReturn(responseMock);

        // Act - Llama a tu método 'buscarPorId'
        ClienteResponseDTO resultado = clienteService.buscarPorId(1);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        verify(clienteRepository, times(1)).findById(1);
    }

    @Test
    void testBuscarPorId_NoEncontrado() {
        // Arrange
        when(clienteRepository.findById(99)).thenReturn(Optional.empty());

        // Act & Assert - Verifica que lanza tu ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> {
            clienteService.buscarPorId(99);
        });
        verify(clienteRepository, times(1)).findById(99);
    }

    @Test
    void testListarTodos_Exito() {
        // Arrange
        when(clienteRepository.findAll()).thenReturn(List.of(clienteMock));
        when(clienteMapper.toResponseDTO(any(Cliente.class))).thenReturn(responseMock);

        // Act - Llama a tu método 'listarTodos'
        List<ClienteResponseDTO> resultados = clienteService.listarTodos();

        // Assert
        assertNotNull(resultados);
        assertFalse(resultados.isEmpty());
        assertEquals(1, resultados.size());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void testActualizar_Exito() {
        // Arrange
        when(clienteRepository.findById(1)).thenReturn(Optional.of(clienteMock));
        doNothing().when(clienteMapper).actualizarEntidad(any(ClientesRequestDTO.class), any(Cliente.class));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteMock);
        when(clienteMapper.toResponseDTO(any(Cliente.class))).thenReturn(responseMock);

        // Act - Llama a tu método 'actualizar'
        ClienteResponseDTO resultado = clienteService.actualizar(1, requestMock);

        // Assert
        assertNotNull(resultado);
        verify(clienteRepository, times(1)).findById(1);
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void testEliminar_Exito() {
        // Arrange
        when(clienteRepository.findById(1)).thenReturn(Optional.of(clienteMock));
        doNothing().when(clienteRepository).delete(any(Cliente.class));

        // Act - Llama a tu método 'eliminar'
        assertDoesNotThrow(() -> clienteService.eliminar(1));

        // Assert
        verify(clienteRepository, times(1)).findById(1);
        verify(clienteRepository, times(1)).delete(any(Cliente.class));
    }
}