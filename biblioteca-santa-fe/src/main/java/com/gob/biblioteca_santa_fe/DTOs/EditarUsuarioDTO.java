package com.gob.biblioteca_santa_fe.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditarUsuarioDTO {
    private String username;

    @Email(message = "mail debe ser valido")
    @NotBlank(message = "se debe ingresar mail")
    private String email;
    
    @NotBlank(message = "se debe ingresar un nombre")
    private String nombre;

    @NotBlank(message = "se debe ingresar apellido")
    private String apellido;

    @NotBlank(message = "se debe ingresar dni")
    @Pattern(regexp = "\\d{8}", message = "dni de 8 digitos")
    private String dni;

    @NotBlank(message = "se debe ingresar contraseña")
    private String password;

    private LocalDate fecha_nacimiento;


}
