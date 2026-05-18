package com.example.ms_reportes.dto;

import com.example.ms_reportes.dto.PagoDTO;
import com.example.ms_reportes.dto.ReservaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class ReporteConsolidadoDTO {
    private String mensaje;
    private Integer totalPagos;
    private Integer totalReservas;
    private List<PagoDTO> detallePagos;
    private List<ReservaDTO> detalleReservas;
}