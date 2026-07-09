package com.example.ms_clientes.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DireccionRequestDTO {

    @NotBlank(message = "La calle no puede estar vacía")
    @Size(min = 5, max = 100, message = "La calle debe tener entre 5 y 100 caracteres")
    private String calle;

    @NotNull(message = "El número es obligatorio")
    @Positive(message = "El número debe ser mayor a 0")
    private Integer numero;

    @NotBlank(message = "La ciudad no puede estar vacía")
    @Size(min = 3, max = 50, message = "La ciudad debe tener entre 3 y 50 caracteres")
    private String ciudad;

    @Builder.Default
    private boolean principal = true;

    @NotNull(message = "La fecha de registro es obligatoria")
    @PastOrPresent(message = "La fecha de registro no puede ser en el futuro")
    private LocalDateTime fechaRegistro;
}