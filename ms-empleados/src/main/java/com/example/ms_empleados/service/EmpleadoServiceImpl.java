package com.example.ms_empleados.service;

import com.example.ms_empleados.dto.EmpleadoRequestDTO;
import com.example.ms_empleados.dto.EmpleadoResponseDTO;
import com.example.ms_empleados.model.Empleado;
import com.example.ms_empleados.exception.ResourceNotFoundException;
import com.example.ms_empleados.mapper.EmpleadoMapper;
import com.example.ms_empleados.repository.EmpleadoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j // Agregamos la anotación de Lombok para SLF4J
public class EmpleadoServiceImpl implements IEmpleadoService {

    private final EmpleadoRepository repository;
    private final EmpleadoMapper mapper;

    public EmpleadoServiceImpl(EmpleadoRepository repository, EmpleadoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public EmpleadoResponseDTO crear(EmpleadoRequestDTO request) {
        log.info("Procesando creación de nuevo empleado con RUT: {}", request.getRut());
        Empleado nuevoEmpleado = mapper.toEntity(request);
        Empleado empleadoGuardado = repository.save(nuevoEmpleado);
        log.info("Empleado creado exitosamente con ID: {}", empleadoGuardado.getId());
        return mapper.toResponseDTO(empleadoGuardado);
    }

    @Override
    public List<EmpleadoResponseDTO> listarTodos() {
        log.info("Obteniendo todos los empleados de la base de datos");
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmpleadoResponseDTO buscarPorId(Integer id) {
        log.info("Buscando empleado en la base de datos con ID: {}", id);
        Empleado empleado = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Error al buscar: Empleado con ID {} no encontrado", id);
                    return new ResourceNotFoundException("Empleado con ID " + id + " no encontrado");
                });
        return mapper.toResponseDTO(empleado);
    }

    @Override
    public EmpleadoResponseDTO actualizar(Integer id, EmpleadoRequestDTO request) {
        log.info("Iniciando actualización del empleado con ID: {}", id);
        Empleado empleadoExistente = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Error al actualizar: Empleado con ID {} no encontrado", id);
                    return new ResourceNotFoundException("No se puede actualizar. Empleado con ID " + id + " no encontrado");
                });

        empleadoExistente.setRut(request.getRut());
        empleadoExistente.setNombreCompleto(request.getNombreCompleto());
        empleadoExistente.setCargo(request.getCargo());
        empleadoExistente.setSueldoBase(request.getSueldoBase());
        empleadoExistente.setConContratoIndefinido(request.getConContratoIndefinido());
        empleadoExistente.setFechaContratacion(request.getFechaContratacion());
        empleadoExistente.setActivo(request.getActivo());

        Empleado empleadoActualizado = repository.save(empleadoExistente);
        log.info("Empleado con ID {} actualizado correctamente", id);
        return mapper.toResponseDTO(empleadoActualizado);
    }

    @Override
    public void eliminar(Integer id) {
        log.info("Iniciando eliminación del empleado con ID: {}", id);
        Empleado empleado = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Error al eliminar: Empleado con ID {} no encontrado", id);
                    return new ResourceNotFoundException("No se puede eliminar. Empleado con ID " + id + " no encontrado");
                });
        repository.delete(empleado);
        log.info("Empleado con ID {} eliminado correctamente", id);
    }

    @Override
    public List<EmpleadoResponseDTO> buscarActivosPorAnio(Integer anio) {
        log.info("Buscando empleados activos contratados en el año: {}", anio);
        return repository.findEmpleadosActivosByYear(anio).stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}