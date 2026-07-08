package com.example.ms_pagos.mapper;

import com.example.ms_pagos.dto.PagoRequestDTO;
import com.example.ms_pagos.dto.PagoResponseDTO;
import com.example.ms_pagos.model.Pago;
import org.springframework.stereotype.Component;

@Component
public class PagoMapper {

    public Pago toEntity(PagoRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Pago pago = new Pago();
        pago.setReservaId(dto.getReservaId());
        pago.setMonto(dto.getMonto());
        return pago;
    }

    public PagoResponseDTO toResponseDTO(Pago entity) {
        if (entity == null) {
            return null;
        }
        PagoResponseDTO.PagoResponseDTOBuilder builder = PagoResponseDTO.builder()
                .id(entity.getId())
                .codigoTransaccion(entity.getCodigoTransaccion())
                .reservaId(entity.getReservaId())
                .monto(entity.getMonto())
                .fechaPago(entity.getFechaPago())
                .estadoPago(entity.getEstadoPago());

        if (entity.getMetodoPago() != null) {
            builder.metodoPagoId(entity.getMetodoPago().getId())
                   .metodoPagoNombre(entity.getMetodoPago().getNombre());
        }

        return builder.build();
    }
}
