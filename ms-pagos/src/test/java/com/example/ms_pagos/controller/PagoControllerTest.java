package com.example.ms_pagos.controller;

import com.example.ms_pagos.assemblers.PagoModelAssembler;
import com.example.ms_pagos.dto.PagoRequestDTO;
import com.example.ms_pagos.dto.PagoResponseDTO;
import com.example.ms_pagos.service.PagoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PagoController.class)
@Import(PagoModelAssembler.class) // Importante para que HATEOAS funcione en el test
public class PagoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PagoService pagoService;

    @Autowired
    private ObjectMapper objectMapper;

    private PagoResponseDTO pagoResponseDTO;

    @BeforeEach
    void setUp() {
        pagoResponseDTO = PagoResponseDTO.builder()
                .id(1)
                .codigoTransaccion("TRX-TEST")
                .reservaId(10)
                .monto(new BigDecimal("15000.00"))
                .fechaPago(LocalDateTime.now())
                .estadoPago("APROBADO")
                .metodoPagoId(1)
                .metodoPagoNombre("Tarjeta")
                .build();
    }

    @Test
    void listarTodos_RetornaListaConHateoas() throws Exception {
        // Given
        List<PagoResponseDTO> pagos = Arrays.asList(pagoResponseDTO);
        when(pagoService.obtenerTodos()).thenReturn(pagos);

        // When & Then
        mockMvc.perform(get("/api/pagos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.pagoResponseDTOList[0].id").value(1))
                .andExpect(jsonPath("$._embedded.pagoResponseDTOList[0].codigoTransaccion").value("TRX-TEST"))
                // Verificar que HATEOAS generó los links
                .andExpect(jsonPath("$._embedded.pagoResponseDTOList[0]._links.self.href").exists())
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void buscarPorId_RetornaPagoConHateoas() throws Exception {
        // Given
        when(pagoService.obtenerPorId(1)).thenReturn(pagoResponseDTO);

        // When & Then
        mockMvc.perform(get("/api/pagos/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.codigoTransaccion").value("TRX-TEST"))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.pagos.href").exists());
    }

    @Test
    void crearPago_RetornaCreated() throws Exception {
        // Given
        PagoRequestDTO requestDTO = PagoRequestDTO.builder()
                .reservaId(10)
                .monto(new BigDecimal("15000.00"))
                .metodoPagoId(1)
                .build();

        when(pagoService.registrarPago(any(PagoRequestDTO.class))).thenReturn(pagoResponseDTO);

        // When & Then
        mockMvc.perform(post("/api/pagos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.codigoTransaccion").value("TRX-TEST"));
    }

    @Test
    void buscarPorRango_RetornaPaginaConHateoas() throws Exception {
        // Given
        Page<PagoResponseDTO> page = new PageImpl<>(Arrays.asList(pagoResponseDTO));

        when(pagoService.obtenerPagosPorRango(
                any(BigDecimal.class),
                any(BigDecimal.class),
                anyInt(),
                anyInt())).thenReturn(page);

        // When & Then
        mockMvc.perform(get("/api/pagos/rango")
                        .param("min", "10000.00")
                        .param("max", "20000.00")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.pagoResponseDTOList[0].id").value(1))
                .andExpect(jsonPath("$.page.totalElements").value(1))
                // Verificar enlaces de paginación HATEOAS
                .andExpect(jsonPath("$._links.self.href").exists());
    }
}