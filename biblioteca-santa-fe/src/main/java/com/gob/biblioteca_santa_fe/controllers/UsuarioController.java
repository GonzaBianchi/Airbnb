package com.gob.biblioteca_santa_fe.controllers;

import com.gob.biblioteca_santa_fe.DTOs.EditarUsuarioDTO;
import com.gob.biblioteca_santa_fe.DTOs.UsuarioDTO;
import com.gob.biblioteca_santa_fe.model.Usuario;
import com.gob.biblioteca_santa_fe.interfaces.UsuarioService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        try {
            Usuario nuevoUsuario = usuarioService.registrarUsuario(usuarioDTO);
            return ResponseEntity.ok("Usuario registrado exitosamente con ID: " + nuevoUsuario.getId());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> findAll(){
        List<Usuario> usuarios= usuarioService.findAll();
        return  ResponseEntity.ok(usuarios);
    }

    @PostMapping("/crear")
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario){

        return ResponseEntity.ok(usuarioService.crearUsuario(usuario));
    }
    @PutMapping("/edit/{id}")
    public ResponseEntity<String> editarUsuario(@RequestBody Usuario usuario, @PathVariable Long id) {
        try {
            usuarioService.editarUsuario(usuario, id);
            return ResponseEntity.ok("User profile updated successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/modificar")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> updateUser(@Validated @RequestBody EditarUsuarioDTO editarUsuarioDTO, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            System.out.println("UserDetails es null");
            return ResponseEntity.status(401).body("el usuario no esta autenticado");
        }

        String authenticatedUsername = userDetails.getUsername();
        System.out.println("usuario autenticado: " + authenticatedUsername);

        if (!authenticatedUsername.equals(editarUsuarioDTO.getUsername())) {
            return ResponseEntity.status(403).body("no coinciden usuarios");
        }

        usuarioService.actualizarUsuario(editarUsuarioDTO, authenticatedUsername);
        return ResponseEntity.ok("ok");
    }
}
