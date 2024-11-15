package com.gob.biblioteca_santa_fe.DTOs;

import java.util.Set;

import com.gob.biblioteca_santa_fe.model.TipoUsuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthResponse {
    private String token;
    private String username;
    private Set<TipoUsuario> tipoUsuario;
}
