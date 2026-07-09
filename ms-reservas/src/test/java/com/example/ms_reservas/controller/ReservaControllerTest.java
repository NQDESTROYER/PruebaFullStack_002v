package com.example.ms_reservas.controller;

import com.example.ms_reservas.assemblers.ReservaModelAssembler;
import com.example.ms_reservas.dto.ReservaRequestDTO;
import com.example.ms_reservas.dto.ReservaResponseDTO;
import com.example.ms_reservas.service.ReservaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservaController.class)
class ReservaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Usamos @MockBean porque Spring necesita inyectar este mock en el contexto web
    @MockBean
    private ReservaService reservaService;

    @MockBean
    private ReservaModelAssembler assembler;

    private ReservaResponseDTO responseDTO;
    private ReservaRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        responseDTO = new ReservaResponseDTO();
        responseDTO.setId(1);
        responseDTO.setCodigoReserva("RES-001");
        responseDTO.setMontoTotal(new BigDecimal("15000.00"));

        requestDTO = new ReservaRequestDTO();
        requestDTO.setCodigoReserva("RES-001");
        requestDTO.setFechaInicio(LocalDate.now().plusDays(1));
        requestDTO.setFechaFin(LocalDate.now().plusDays(5));
        requestDTO.setClienteId(1);
        requestDTO.setVehiculoId(1);
        requestDTO.setEstadoId(1);
        requestDTO.setMontoTotal(new BigDecimal("15000.00"));
        requestDTO.setSeguroIncluido(true);
    }

    @Test
    void obtenerPorId_DebeRetornarStatus200() throws Exception {
        // Arrange
        Mockito.when(reservaService.obtenerPorId(1)).thenReturn(responseDTO);
        Mockito.when(assembler.toModel(responseDTO)).thenReturn(EntityModel.of(responseDTO));

        // Act & Assert
        mockMvc.perform(get("/api/v1/reservas/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.codigoReserva").value("RES-001"));
    }

    @Test
    void crearReserva_DebeRetornarStatus201() throws Exception {
        // Arrange
        Mockito.when(reservaService.crear(any(ReservaRequestDTO.class))).thenReturn(responseDTO);
        Mockito.when(assembler.toModel(responseDTO)).thenReturn(EntityModel.of(responseDTO));

        // Act & Assert
        mockMvc.perform(post("/api/v1/reservas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }
}