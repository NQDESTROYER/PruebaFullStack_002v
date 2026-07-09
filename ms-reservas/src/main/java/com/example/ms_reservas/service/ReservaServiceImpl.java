package com.example.ms_reservas.service;

import com.example.ms_reservas.client.ClienteClient;
import com.example.ms_reservas.client.VehiculoClient;
import com.example.ms_reservas.dto.ReservaRequestDTO;
import com.example.ms_reservas.dto.ReservaResponseDTO;
import com.example.ms_reservas.model.EstadoReserva;
import com.example.ms_reservas.model.Reserva;
import com.example.ms_reservas.exception.ResourceNotFoundException;
import com.example.ms_reservas.mapper.ReservaMapper;
import com.example.ms_reservas.repository.EstadoReservaRepository;
import com.example.ms_reservas.repository.ReservaRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final EstadoReservaRepository estadoRepository;
    private final ReservaMapper reservaMapper;
    private final ClienteClient clienteClient;
    private final VehiculoClient vehiculoClient;

    @Override
    @Transactional(readOnly = true)
    public List<ReservaResponseDTO> listarTodas() {
        log.info("Listando todas las reservas");
        return reservaRepository.findAll().stream().map(reservaMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservaResponseDTO> buscarDesdeFecha(LocalDate fecha) {
        log.info("Buscando reservas desde la fecha: {}", fecha);
        return reservaRepository.buscarDesdeFecha(fecha).stream()
                .map(reservaMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ReservaResponseDTO obtenerPorId(Integer id) {
        return reservaRepository.findById(id).map(reservaMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con ID: " + id));
    }

    @Override
    @Transactional
    public ReservaResponseDTO crear(ReservaRequestDTO dto) {
        log.info("Creando reserva, validando reglas de negocio y datos externos...");

        // Regla de Negocio 1: Coherencia de fechas
        if (dto.getFechaFin().isBefore(dto.getFechaInicio()) || dto.getFechaFin().isEqual(dto.getFechaInicio())) {
            log.warn("Intento de reserva con fechas inconsistentes");
            throw new IllegalArgumentException("La fecha de fin debe ser estrictamente posterior a la fecha de inicio.");
        }

        // Regla de Negocio 2: Validar cliente externo
        try {
            clienteClient.obtenerClientePorId(dto.getClienteId());
            log.info("Cliente {} validado correctamente", dto.getClienteId());
        } catch (FeignException e) {
            log.error("Fallo comunicación con ms-clientes o cliente no existe. Error: {}", e.status());
            throw new ResourceNotFoundException("El cliente ID " + dto.getClienteId() + " no existe o el servicio no responde.");
        }

        // Regla de Negocio 3: Validar vehículo externo
        try {
            vehiculoClient.obtenerVehiculoPorId(dto.getVehiculoId());
            log.info("Vehículo {} validado correctamente", dto.getVehiculoId());
        } catch (FeignException e) {
            log.error("Fallo comunicación con ms-vehiculos o vehículo no existe. Error: {}", e.status());
            throw new ResourceNotFoundException("El vehículo ID " + dto.getVehiculoId() + " no existe o el servicio no responde.");
        }

        // Regla de Negocio 4: Validar disponibilidad (evitar doble arriendo simultáneo)
        // Asumimos que un auto con estado "CONFIRMADA" no puede ser arrendado nuevamente.
        boolean autoOcupado = reservaRepository.existsByVehiculoIdAndEstado_NombreEstado(dto.getVehiculoId(), "CONFIRMADA");
        if (autoOcupado) {
            log.warn("El vehículo ID {} ya se encuentra con una reserva confirmada.", dto.getVehiculoId());
            throw new IllegalArgumentException("El vehículo seleccionado ya se encuentra reservado y no está disponible.");
        }

        EstadoReserva estado = estadoRepository.findById(dto.getEstadoId())
                .orElseThrow(() -> new ResourceNotFoundException("Estado de reserva inválido con ID: " + dto.getEstadoId()));

        Reserva reserva = reservaMapper.toEntity(dto);
        reserva.setEstado(estado);

        log.info("Guardando nueva reserva en base de datos");
        return reservaMapper.toDto(reservaRepository.save(reserva));
    }

    @Override
    @Transactional
    public ReservaResponseDTO actualizar(Integer id, ReservaRequestDTO dto) {
        log.info("Actualizando reserva ID: {}", id);
        Reserva reservaExistente = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con ID: " + id));

        // Mismas reglas de negocio para fechas en la actualización
        if (dto.getFechaFin().isBefore(dto.getFechaInicio()) || dto.getFechaFin().isEqual(dto.getFechaInicio())) {
            throw new IllegalArgumentException("La fecha de fin debe ser estrictamente posterior a la fecha de inicio.");
        }

        EstadoReserva estado = estadoRepository.findById(dto.getEstadoId())
                .orElseThrow(() -> new ResourceNotFoundException("Estado de reserva inválido con ID: " + dto.getEstadoId()));

        reservaExistente.setCodigoReserva(dto.getCodigoReserva());
        reservaExistente.setFechaInicio(dto.getFechaInicio());
        reservaExistente.setFechaFin(dto.getFechaFin());
        reservaExistente.setMontoTotal(dto.getMontoTotal());
        reservaExistente.setSeguroIncluido(dto.getSeguroIncluido());
        reservaExistente.setClienteId(dto.getClienteId());
        reservaExistente.setVehiculoId(dto.getVehiculoId());
        reservaExistente.setEstado(estado);

        return reservaMapper.toDto(reservaRepository.save(reservaExistente));
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        log.info("Eliminando reserva ID: {}", id);
        if(!reservaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reserva inexistente con ID: " + id);
        }
        reservaRepository.deleteById(id);
    }
}