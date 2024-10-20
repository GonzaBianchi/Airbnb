package com.gob.biblioteca_santa_fe.DTOs;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UsuarioDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    private String dni;
    private String rol;
    // private Long idTipoUsuario;

    public boolean isValidRol() {
        return rol.equals("inquilino") || rol.equals("anfitrión");
    }
}
