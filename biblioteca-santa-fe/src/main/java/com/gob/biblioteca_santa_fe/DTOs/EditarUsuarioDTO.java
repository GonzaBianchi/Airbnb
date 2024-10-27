package com.gob.biblioteca_santa_fe.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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

    @Email(message = "se debe ingresar apellido")
    @NotBlank(message = "se debe ingresar apellido")
    private String email;
    
    @NotBlank(message = "se debe ingresar un nombre")
    private String nombre;

    @NotBlank(message = "se debe ingresar apelludo")
    private String apellido;

}
