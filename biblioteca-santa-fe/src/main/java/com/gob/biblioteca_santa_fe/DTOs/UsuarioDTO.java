package com.gob.biblioteca_santa_fe.DTOs;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Instant;
import java.util.Set;

import com.gob.biblioteca_santa_fe.model.TipoUsuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Data
public class UsuarioDTO {
    private Long id;

    private String username;

    @NotBlank(message = "se debe agregar correo")
    @Pattern(regexp = ".+@.+\\..+", message = "debe ser un correo valido")
    private String email;

    @NotBlank(message = "se debe agregar contraseña")
    private String password;

    @Pattern(regexp = "\\d{8}", message = "dni de 8 digitos")
    private String dni;

    @NotBlank(message = "se debe agregar nombre")
    private String nombre;

    @NotBlank(message = "se debe agregar apellido")
    private String apellido;

    private LocalDate fecha_nacimiento;

    private Instant fecha_creacion;

    private LocalDate fecha_modificacion;

    private Set<TipoUsuario> tipoUsuarios;

    // private Long idTipoUsuario;
}
