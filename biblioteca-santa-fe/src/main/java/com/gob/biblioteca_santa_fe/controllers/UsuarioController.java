package com.gob.biblioteca_santa_fe.controllers;

import com.gob.biblioteca_santa_fe.DTOs.UsuarioDTO;
import com.gob.biblioteca_santa_fe.model.Usuario;
import com.gob.biblioteca_santa_fe.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        try {
            Usuario nuevoUsuario = usuarioService.registrarUsuario(usuarioDTO);
            return ResponseEntity.ok("Usuario registrado exitosamente con ID: " + nuevoUsuario.getId());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}