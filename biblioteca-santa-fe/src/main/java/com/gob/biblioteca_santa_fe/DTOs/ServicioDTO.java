package com.gob.biblioteca_santa_fe.DTOs;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class ServicioDTO {
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private boolean borrado;
}