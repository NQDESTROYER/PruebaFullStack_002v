package com.example.ms_reservas.mapper;

import com.example.ms_reservas.dto.ReservaRequestDTO;
import com.example.ms_reservas.dto.ReservaResponseDTO;
import com.example.ms_reservas.entity.Reserva;
import org.springframework.stereotype.Component;

@Component
public class ReservaMapper {
    public Reserva toEntity(ReservaRequestDTO dto) {
        if (dto == null) return null;
        Reserva reserva = new Reserva();
        reserva.setCodigoReserva(dto.getCodigoReserva());
        reserva.setFechaInicio(dto.getFechaInicio());
        reserva.setFechaFin(dto.getFechaFin());
        reserva.setMontoTotal(dto.getMontoTotal());
        reserva.setSeguroIncluido(dto.getSeguroIncluido());
        reserva.setClienteId(dto.getClienteId());
        reserva.setVehiculoId(dto.getVehiculoId());
        return reserva;
    }

    public ReservaResponseDTO toDto(Reserva entity) {
        if (entity == null) return null;
        ReservaResponseDTO dto = new ReservaResponseDTO();
        dto.setId(entity.getId());
        dto.setCodigoReserva(entity.getCodigoReserva());
        dto.setFechaInicio(entity.getFechaInicio());
        dto.setFechaFin(entity.getFechaFin());
        dto.setMontoTotal(entity.getMontoTotal());
        dto.setSeguroIncluido(entity.getSeguroIncluido());
        dto.setClienteId(entity.getClienteId());
        dto.setVehiculoId(entity.getVehiculoId());

        if (entity.getEstado() != null) {
            dto.setEstadoId(entity.getEstado().getId());
            dto.setNombreEstado(entity.getEstado().getNombreEstado());
        }
        return dto;
    }
}
