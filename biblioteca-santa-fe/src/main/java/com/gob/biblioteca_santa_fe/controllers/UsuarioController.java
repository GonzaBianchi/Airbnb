package com.gob.biblioteca_santa_fe.controllers;

import com.gob.biblioteca_santa_fe.DTOs.EditarUsuarioDTO;
import com.gob.biblioteca_santa_fe.DTOs.UsuarioDTO;
import com.gob.biblioteca_santa_fe.model.Usuario;
import com.gob.biblioteca_santa_fe.interfaces.UsuarioService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> findAll() {
        System.out.println("Solicitando lista de usuarios...");
        List<Usuario> usuarios = usuarioService.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping("/crear")
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.crearUsuario(usuario));
    }
    @PutMapping("/modificar")
    public ResponseEntity<String> modificarUser(@Validated @RequestBody EditarUsuarioDTO editarUsuarioDTO,
            @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            System.out.println("UserDetails es null");
            return ResponseEntity.status(401).body("el usuario no esta autenticado");
        }

        String authenticatedUsername = userDetails.getUsername();
        System.out.println("usuario autenticado: " + authenticatedUsername);

        if (!authenticatedUsername.equals(editarUsuarioDTO.getUsername())) {
            return ResponseEntity.status(403).body("no coinciden usuarios");
        }

        usuarioService.modificarUsuario(editarUsuarioDTO, authenticatedUsername);
        return ResponseEntity.ok("usuario modificado exitosamente");
    }
}
