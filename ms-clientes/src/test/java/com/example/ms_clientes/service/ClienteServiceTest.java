package com.example.ms_clientes.service;

import com.example.ms_clientes.dto.ClienteResponseDTO;
import com.example.ms_clientes.dto.ClientesRequestDTO;
import com.example.ms_clientes.dto.DireccionRequestDTO;
import com.example.ms_clientes.dto.DireccionResponseDTO;
import com.example.ms_clientes.exception.ResourceNotFoundException;
import com.example.ms_clientes.mapper.ClienteMapper;
import com.example.ms_clientes.model.Cliente;
import com.example.ms_clientes.model.Direccion;
import com.example.ms_clientes.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteMapper clienteMapper;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private Cliente cliente;
    private Direccion direccion;
    private ClientesRequestDTO requestDTO;
    private ClienteResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        direccion = new Direccion();
        direccion.setId(1);
        direccion.setCalle("Los Alerces");
        direccion.setNumero(123);
        direccion.setPrincipal(true);

        cliente = new Cliente();
        cliente.setId(1);
        cliente.setRut("12345678-9");
        cliente.setDirecciones(new ArrayList<>());
        cliente.addDireccion(direccion);

        requestDTO = new ClientesRequestDTO();
        requestDTO.setRut("12345678-9");

        responseDTO = new ClienteResponseDTO();
        responseDTO.setId(1);
        responseDTO.setRut("12345678-9");
    }

    @Test
    void crearCliente_Exitoso() {
        when(clienteMapper.toEntity(any())).thenReturn(cliente);
        when(clienteRepository.save(any())).thenReturn(cliente);
        when(clienteMapper.toResponseDTO(any())).thenReturn(responseDTO);

        ClienteResponseDTO resultado = clienteService.crear(requestDTO);

        assertNotNull(resultado);
        assertEquals("12345678-9", resultado.getRut());
        verify(clienteRepository, times(1)).save(any());
    }

    @Test
    void buscarPorId_Exitoso() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        when(clienteMapper.toResponseDTO(cliente)).thenReturn(responseDTO);

        ClienteResponseDTO resultado = clienteService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
    }

    @Test
    void eliminar_Exitoso() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));

        clienteService.eliminar(1);

        verify(clienteRepository, times(1)).delete(cliente);
    }

    @Test
    void obtenerDireccionPorClienteId_Exitosa() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));

        DireccionResponseDTO resultado = clienteService.obtenerDireccionPorClienteId(1);

        assertNotNull(resultado);
        assertEquals("Los Alerces", resultado.getCalle());
        assertTrue(resultado.isPrincipal());
    }

    @Test
    void obtenerDireccionPorClienteId_NoEncontrada_DebeLanzarExcepcion() {
        cliente.getDirecciones().clear(); // Quitamos las direcciones
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));

        assertThrows(ResourceNotFoundException.class, () -> clienteService.obtenerDireccionPorClienteId(1));
    }
}