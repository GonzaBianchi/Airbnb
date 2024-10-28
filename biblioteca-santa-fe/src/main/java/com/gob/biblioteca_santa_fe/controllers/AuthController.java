
package com.gob.biblioteca_santa_fe.controllers;
import com.gob.biblioteca_santa_fe.DTOs.AuthRequest;
import com.gob.biblioteca_santa_fe.DTOs.AuthResponse;
import com.gob.biblioteca_santa_fe.DTOs.UsuarioDTO;
import com.gob.biblioteca_santa_fe.model.TipoUsuario;
import com.gob.biblioteca_santa_fe.model.Usuario;
import com.gob.biblioteca_santa_fe.services.JwtService;
import com.gob.biblioteca_santa_fe.services.TipoUsuarioServiceImpl;
import com.gob.biblioteca_santa_fe.services.UsuarioServiceImpl;

import jakarta.validation.Valid;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TipoUsuarioServiceImpl tipoUsuarioService;
    @Autowired
    private UsuarioServiceImpl usuarioService;


    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails, new HashMap<>());
        return new AuthResponse(token);
    }

    @PostMapping("/register")
    public Usuario register(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setDni(usuarioDTO.getDni());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setFecha_nacimiento(usuarioDTO.getFecha_nacimiento());
        usuario.setFecha_creacion(Instant.now());

        Set<TipoUsuario> tiposUsuariosCargados = new HashSet<>();
        if (usuarioDTO.getTipoUsuarios() != null) {
            for (TipoUsuario tipoUsuario : usuarioDTO.getTipoUsuarios()) {
                TipoUsuario tipoUsuarioCargado = tipoUsuarioService.findById(tipoUsuario.getId())
                        .orElseThrow(() -> new RuntimeException("TipoUsuario no encontrado: " + tipoUsuario.getId()));
                tiposUsuariosCargados.add(tipoUsuarioCargado);
            }
        }
        usuario.setTipoUsuarios(tiposUsuariosCargados);

        return usuarioService.crearUsuario(usuario);
    }
}