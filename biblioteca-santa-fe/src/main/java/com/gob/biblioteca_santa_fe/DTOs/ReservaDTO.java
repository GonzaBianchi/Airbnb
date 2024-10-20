package com.gob.biblioteca_santa_fe.DTOs;

import lombok.Data;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
public class ReservaDTO {
    private Long id;

    private HospedajeDTO hospedaje;
    private UsuarioDTO usuario;
    private LocalDate fechaCheckIn;
    private LocalDate fechaCheckOut;
    @Min(value = 0, message = "La cantidad de niños no puede ser negativa")
    private Integer cantNinos;
    @Min(value = 0, message = "La cantidad de adultos no puede ser negativa")
    private Integer cantAdultos;
    @Min(value = 0, message = "La cantidad de bebés no puede ser negativa")
    private Integer cantBebes;
    @Min(value = 0, message = "La cantidad de mascotas no puede ser negativa")
    private Integer cantMascotas;
    @DecimalMin(value = "0.0", inclusive = false, message = "El importe total debe ser mayor que 0")
    private BigDecimal importeTotal;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
}