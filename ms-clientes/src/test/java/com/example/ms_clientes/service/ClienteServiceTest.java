package com.example.ms_clientes.service;

import com.example.ms_clientes.dto.ClienteResponseDTO;
import com.example.ms_clientes.model.Cliente;
import com.example.ms_clientes.mapper.ClienteMapper;
import com.example.ms_clientes.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository; // Simulamos la base de datos

    @Mock
    private ClienteMapper clienteMapper;         // Simulamos el traductor de datos

    @InjectMocks
    private ClienteServiceImpl clienteService;   // El servicio real que vamos a probar

    @Test
    void debeBuscarPorIdExitosamente() {

        Integer idBuscado = 1;

        // Creamos un cliente simulado como si viniera de la BD
        Cliente clienteEncontrado = new Cliente();
        clienteEncontrado.setId(idBuscado);
        clienteEncontrado.setNombreCompleto("Juan Perez");

        // Creamos la respuesta simulada que debería entregar el Service
        ClienteResponseDTO responseDTO = new ClienteResponseDTO();
        responseDTO.setId(idBuscado);
        responseDTO.setNombreCompleto("Juan Perez");

        // Le enseñamos a los Mocks cómo reaccionar
        when(clienteRepository.findById(idBuscado)).thenReturn(Optional.of(clienteEncontrado));
        when(clienteMapper.toResponseDTO(clienteEncontrado)).thenReturn(responseDTO);


        ClienteResponseDTO resultado = clienteService.buscarPorId(idBuscado);


        assertNotNull(resultado); // Confirmamos que no falló devolviendo un vacío
        assertEquals(1, resultado.getId()); // Confirmamos que el ID coincide
        assertEquals("Juan Perez", resultado.getNombreCompleto()); // Confirmamos el nombre
    }
}