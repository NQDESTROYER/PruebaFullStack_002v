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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PagoServiceImpl implements PagoService {

    private final PagoRepository pagoRepository;
    private final MetodoPagoRepository metodoPagoRepository;
    private final ReservaClient reservaClient;
    private final PagoMapper pagoMapper;

    public PagoServiceImpl(PagoRepository pagoRepository,
                           MetodoPagoRepository metodoPagoRepository,
                           ReservaClient reservaClient,
                           PagoMapper pagoMapper) {
        this.pagoRepository = pagoRepository;
        this.metodoPagoRepository = metodoPagoRepository;
        this.reservaClient = reservaClient;
        this.pagoMapper = pagoMapper;
    }

    @Override
    public List<PagoResponseDTO> obtenerTodos() {
        return pagoRepository.findAll().stream()
                .map(pagoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PagoResponseDTO obtenerPorId(Integer id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado con ID: " + id));
        return pagoMapper.toResponseDTO(pago);
    }

    @Override
    public PagoResponseDTO registrarPago(PagoRequestDTO dto) {
        // 1. Validar que exista el método de pago
        MetodoPago metodoPago = metodoPagoRepository.findById(dto.getMetodoPagoId())
                .orElseThrow(() -> new ResourceNotFoundException("Método de pago no encontrado con ID: " + dto.getMetodoPagoId()));

        // 2. Validar que exista la reserva (Llamada remota mediante Feign Client)
        try {
            ReservaDTO reserva = reservaClient.obtenerReservaPorId(dto.getReservaId());
            if (reserva == null) {
                throw new ResourceNotFoundException("La reserva con ID " + dto.getReservaId() + " no existe.");
            }
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al comunicarse con el servicio de reservas: " + e.getMessage());
        }

        // 3. Mapear DTO a entidad y enriquecer con lógica de negocio
        Pago pago = pagoMapper.toEntity(dto);
        pago.setMetodoPago(metodoPago);
        pago.setCodigoTransaccion("TRX-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        pago.setFechaPago(LocalDateTime.now());
        pago.setEstadoPago("APROBADO");

        // 4. Guardar y retornar respuesta mapeada
        Pago pagoGuardado = pagoRepository.save(pago);
        return pagoMapper.toResponseDTO(pagoGuardado);
    }

    // 5. NUEVO MÉTODO: Paginación y filtrado por rango de monto
    @Override
    public Page<PagoResponseDTO> obtenerPagosPorRango(BigDecimal min, BigDecimal max, int page, int size) {
        // Configuramos la paginación y le decimos que ordene por fechaPago de forma descendente
        Pageable pageable = PageRequest.of(page, size, Sort.by("fechaPago").descending());

        // Obtenemos la página desde el repositorio
        Page<Pago> pagosPage = pagoRepository.findPagosByMontoRange(min, max, pageable);

        // La interfaz Page tiene un método .map() nativo que facilita mucho convertir de Entidad a DTO
        return pagosPage.map(pagoMapper::toResponseDTO);
    }
}