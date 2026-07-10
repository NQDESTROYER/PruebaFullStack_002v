package com.example.ms_clientes.controller;

import com.example.ms_clientes.dto.ClienteResponseDTO;
import com.example.ms_clientes.dto.ClientesRequestDTO;
import com.example.ms_clientes.dto.DireccionRequestDTO;
import com.example.ms_clientes.dto.DireccionResponseDTO;
import com.example.ms_clientes.service.IClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IClienteService clienteService;

    private ClientesRequestDTO requestDTO;
    private ClienteResponseDTO responseDTO;
    private DireccionRequestDTO dirRequestDTO;
    private DireccionResponseDTO dirResponseDTO;

    @BeforeEach
    void setUp() {
        dirRequestDTO = DireccionRequestDTO.builder()
                .calle("Av. Siempreviva")
                .numero(742)
                .ciudad("Springfield")
                .principal(true)
                .fechaRegistro(LocalDateTime.now().minusDays(1))
                .build();

        requestDTO = ClientesRequestDTO.builder()
                .rut("12345678-9")
                .nombreCompleto("Homero Simpson")
                .email("homero@planta.com")
                .ingresoMensual(BigDecimal.valueOf(1500000))
                .activo(true)
                .fechaNacimiento(LocalDate.of(1956, 5, 12))
                .direcciones(Collections.singletonList(dirRequestDTO))
                .build();

        responseDTO = ClienteResponseDTO.builder()
                .id(1)
                .rut("12345678-9")
                .nombreCompleto("Homero Simpson")
                .build();

        dirResponseDTO = DireccionResponseDTO.builder()
                .id(1)
                .calle("Av. Siempreviva")
                .numero(742)
                .ciudad("Springfield")
                .principal(true)
                .build();
    }

    @Test
    void listarTodos_DebeRetornar200() throws Exception {
        when(clienteService.listarTodos()).thenReturn(Arrays.asList(responseDTO));

        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rut").value("12345678-9"));
    }

    @Test
    void buscarPorId_DebeRetornar200() throws Exception {
        when(clienteService.buscarPorId(1)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void crearCliente_DebeRetornar201() throws Exception {
        when(clienteService.crear(any(ClientesRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombreCompleto").value("Homero Simpson"));
    }

    @Test
    void eliminarCliente_DebeRetornar204() throws Exception {
        mockMvc.perform(delete("/api/clientes/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void obtenerDireccionPorClienteId_DebeRetornar200() throws Exception {
        when(clienteService.obtenerDireccionPorClienteId(1)).thenReturn(dirResponseDTO);

        mockMvc.perform(get("/api/clientes/1/direccion"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.calle").value("Av. Siempreviva"));
    }
}