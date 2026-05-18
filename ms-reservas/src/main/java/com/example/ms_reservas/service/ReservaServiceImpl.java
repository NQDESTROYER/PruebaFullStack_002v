package com.example.ms_reservas.service;

import com.example.ms_reservas.client.ClienteClient;
import com.example.ms_reservas.client.VehiculoClient;
import com.example.ms_reservas.dto.ReservaRequestDTO;
import com.example.ms_reservas.dto.ReservaResponseDTO;
import com.example.ms_reservas.entity.EstadoReserva;
import com.example.ms_reservas.entity.Reserva;
import com.example.ms_reservas.exception.ResourceNotFoundException;
import com.example.ms_reservas.mapper.ReservaMapper;
import com.example.ms_reservas.repository.EstadoReservaRepository;
import com.example.ms_reservas.repository.ReservaRepository;
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

    // Inyectamos los clientes Feign
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
        log.info("Creando reserva, validando datos externos...");

        // 1. Validar que el cliente exista en el ms-clientes (puerto 8081)
        try {
            clienteClient.obtenerClientePorId(dto.getClienteId());
            log.info("Cliente {} validado correctamente", dto.getClienteId());
        } catch (Exception e) {
            log.error("Fallo comunicación con ms-clientes o cliente no existe.");
            throw new ResourceNotFoundException("El cliente ID " + dto.getClienteId() + " no existe o el servicio no responde.");
        }

        // 2. Validar que el vehículo exista en el ms-vehiculos (puerto 8082)
        try {
            vehiculoClient.obtenerVehiculoPorId(dto.getVehiculoId());
            log.info("Vehículo {} validado correctamente", dto.getVehiculoId());
        } catch (Exception e) {
            log.error("Fallo comunicación con ms-vehiculos o vehículo no existe.");
            throw new ResourceNotFoundException("El vehículo ID " + dto.getVehiculoId() + " no existe o el servicio no responde.");
        }

        // 3. Obtener el estado de la reserva local
        EstadoReserva estado = estadoRepository.findById(dto.getEstadoId())
                .orElseThrow(() -> new ResourceNotFoundException("Estado de reserva inválido."));

        // 4. Guardar
        Reserva reserva = reservaMapper.toEntity(dto);
        reserva.setEstado(estado);
        return reservaMapper.toDto(reservaRepository.save(reserva));
    }

    @Override
    @Transactional
    public ReservaResponseDTO actualizar(Integer id, ReservaRequestDTO dto) {
        log.info("Actualizando reserva ID: {}", id);
        Reserva reservaExistente = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con ID: " + id));

        EstadoReserva estado = estadoRepository.findById(dto.getEstadoId())
                .orElseThrow(() -> new ResourceNotFoundException("Estado de reserva inválido."));

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
        if(!reservaRepository.existsById(id)) throw new ResourceNotFoundException("Reserva inexistente");
        reservaRepository.deleteById(id);
    }
}
