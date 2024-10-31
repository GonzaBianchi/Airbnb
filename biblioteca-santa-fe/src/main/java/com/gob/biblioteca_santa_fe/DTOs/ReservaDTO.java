package com.gob.biblioteca_santa_fe.DTOs;

import lombok.Data;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.Instant;
import java.math.BigDecimal;

@Data
public class ReservaDTO {
    private Long id;
    private Long id_hospedaje;
    private Long id_usuario;
    private LocalDate fecha_check_in;
    private LocalDate fecha_check_out;
    @Min(value = 0, message = "La cantidad de niños no puede ser negativa")
    private Integer cant_ninos;
    @Min(value = 0, message = "La cantidad de adultos no puede ser negativa")
    private Integer cant_adultos;
    @Min(value = 0, message = "La cantidad de bebés no puede ser negativa")
    private Integer cant_bebes;
    @Min(value = 0, message = "La cantidad de mascotas no puede ser negativa")
    private Integer cant_mascotas;
    @DecimalMin(value = "0.0", inclusive = false, message = "El importe total debe ser mayor que 0")
    private BigDecimal importe_total;
    private Instant fecha_creacion;
    private Instant fecha_modificacion;
    private String estado;
}