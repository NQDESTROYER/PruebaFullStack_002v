package com.example.ms_reservas.service;

import com.example.ms_reservas.dto.EstadoReservaRequestDTO;
import com.example.ms_reservas.dto.EstadoReservaResponseDTO;
import com.example.ms_reservas.model.EstadoReserva;
import com.example.ms_reservas.exception.ResourceNotFoundException;
import com.example.ms_reservas.mapper.EstadoReservaMapper;
import com.example.ms_reservas.repository.EstadoReservaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EstadoReservaServiceImpl implements EstadoReservaService {

    private final EstadoReservaRepository repository;
    private final EstadoReservaMapper mapper;

    @Override
    public List<EstadoReservaResponseDTO> listarTodos() {
        log.info("Listando todos los estados de reserva");
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public EstadoReservaResponseDTO obtenerPorId(Integer id) {
        log.info("Buscando estado con ID: {}", id);
        return repository.findById(id).map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Estado no encontrado con ID: " + id));
    }

    @Override
    public EstadoReservaResponseDTO crear(EstadoReservaRequestDTO dto) {
        log.info("Creando nuevo estado: {}", dto.getNombreEstado());
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public EstadoReservaResponseDTO actualizar(Integer id, EstadoReservaRequestDTO dto) {
        log.info("Actualizando estado ID: {}", id);
        EstadoReserva estado = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estado no encontrado con ID: " + id));

        estado.setNombreEstado(dto.getNombreEstado());
        estado.setDescripcion(dto.getDescripcion());
        estado.setPermiteModificacion(dto.getPermiteModificacion());
        estado.setNivelPrioridad(dto.getNivelPrioridad());
        estado.setFechaCreacion(dto.getFechaCreacion());
        return mapper.toDto(repository.save(estado));
    }

    @Override
    public void eliminar(Integer id) {
        log.info("Eliminando estado ID: {}", id);
        if(!repository.existsById(id)) throw new ResourceNotFoundException("Estado inexistente con ID: " + id);
        repository.deleteById(id);
    }
}

