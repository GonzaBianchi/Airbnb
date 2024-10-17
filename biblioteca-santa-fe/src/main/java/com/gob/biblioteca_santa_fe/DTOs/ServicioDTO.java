package com.gob.biblioteca_santa_fe.DTOs;

import lombok.Data;
import jakarta.validation.constraints.*;
import java.util.Set;

@Data
public class ServicioDTO {
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private Set<HospedajeDTO> hospedajes;
}