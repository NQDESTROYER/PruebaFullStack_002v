package com.example.ms_clientes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder // 👇 ¡ESTA ANOTACIÓN ES LA QUE HABILITA EL .builder() EN EL MAPPER!
@NoArgsConstructor
@AllArgsConstructor
public class DireccionResponseDTO {

    private Integer id;
    private String calle;
    private Integer numero;
    private String ciudad;
    private boolean principal;
    private LocalDateTime fechaRegistro;
}