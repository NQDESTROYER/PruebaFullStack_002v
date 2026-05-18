package com.example.ms_empleados.service;

import com.example.ms_empleados.dto.EmpleadoRequestDTO;
import com.example.ms_empleados.dto.EmpleadoResponseDTO;
import com.example.ms_empleados.entity.Empleado;
import com.example.ms_empleados.exception.ResourceNotFoundException;
import com.example.ms_empleados.mapper.EmpleadoMapper;
import com.example.ms_empleados.repository.EmpleadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpleadoServiceImpl implements IEmpleadoService {

    private final EmpleadoRepository repository;
    private final EmpleadoMapper mapper;

    // Constructor para inyectar el repositorio y el mapper manual
    public EmpleadoServiceImpl(EmpleadoRepository repository, EmpleadoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public EmpleadoResponseDTO crear(EmpleadoRequestDTO request) {
        Empleado nuevoEmpleado = mapper.toEntity(request);
        Empleado empleadoGuardado = repository.save(nuevoEmpleado);
        return mapper.toResponseDTO(empleadoGuardado);
    }

    @Override
    public List<EmpleadoResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmpleadoResponseDTO buscarPorId(Integer id) {
        Empleado empleado = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado con ID " + id + " no encontrado"));
        return mapper.toResponseDTO(empleado);
    }

    @Override
    public EmpleadoResponseDTO actualizar(Integer id, EmpleadoRequestDTO request) {
        // Verificar si existe el empleado antes de editarlo
        Empleado empleadoExistente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se puede actualizar. Empleado con ID " + id + " no encontrado"));

        // Modificamos los valores antiguos con los nuevos datos de entrada
        empleadoExistente.setRut(request.getRut());
        empleadoExistente.setNombreCompleto(request.getNombreCompleto());
        empleadoExistente.setCargo(request.getCargo());
        empleadoExistente.setSueldoBase(request.getSueldoBase());
        empleadoExistente.setConContratoIndefinido(request.getConContratoIndefinido());
        empleadoExistente.setFechaContratacion(request.getFechaContratacion());

        Empleado empleadoActualizado = repository.save(empleadoExistente);
        return mapper.toResponseDTO(empleadoActualizado);
    }

    @Override
    public void eliminar(Integer id) {
        // Verificar si existe el empleado antes de borrarlo
        Empleado empleado = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se puede eliminar. Empleado con ID " + id + " no encontrado"));
        repository.delete(empleado);
    }
}
