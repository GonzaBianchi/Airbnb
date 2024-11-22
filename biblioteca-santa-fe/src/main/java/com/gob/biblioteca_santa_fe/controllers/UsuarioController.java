package com.gob.biblioteca_santa_fe.controllers;

import com.gob.biblioteca_santa_fe.DTOs.EditarUsuarioDTO;
import com.gob.biblioteca_santa_fe.model.Usuario;
import com.gob.biblioteca_santa_fe.interfaces.UsuarioService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{username}")
    public ResponseEntity<Optional<Usuario>> getUserByUsername(@PathVariable String username) {

        return ResponseEntity.ok(usuarioService.findByUsername(username));

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
