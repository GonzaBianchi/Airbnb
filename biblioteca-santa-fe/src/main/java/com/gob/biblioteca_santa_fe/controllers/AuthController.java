
package com.gob.biblioteca_santa_fe.controllers;

import com.gob.biblioteca_santa_fe.DTOs.AuthRequest;
import com.gob.biblioteca_santa_fe.DTOs.AuthResponse;
import com.gob.biblioteca_santa_fe.DTOs.UsuarioDTO;
import com.gob.biblioteca_santa_fe.model.TipoUsuario;
import com.gob.biblioteca_santa_fe.model.Usuario;
import com.gob.biblioteca_santa_fe.services.JwtService;
import com.gob.biblioteca_santa_fe.services.TipoUsuarioServiceImpl;
import com.gob.biblioteca_santa_fe.services.UsuarioServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
    try {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Usuario usuario = usuarioService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        Map<String, Object> extraClaims = new HashMap<>();
        String token = jwtService.generateToken(userDetails, usuario.getTipoUsuarios(), extraClaims);
        
        AuthResponse response = new AuthResponse(token, usuario.getUsername(), usuario.getTipoUsuarios());
        return ResponseEntity.ok(response);
    } catch (BadCredentialsException ex) {
        AuthResponse errorResponse = new AuthResponse("Credenciales inválidas", null, null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    } catch (Exception ex) {
        AuthResponse errorResponse = new AuthResponse("Error al iniciar sesión", null, null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
    


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            if (usuarioService.existsByEmail(usuarioDTO.getEmail())) {
                throw new RuntimeException("El correo electrónico ya está en uso: " + usuarioDTO.getEmail());
            }
    
            Usuario usuario = Usuario.builder()
                    .username(usuarioDTO.getUsername())
                    .password(passwordEncoder.encode(usuarioDTO.getPassword()))
                    .email(usuarioDTO.getEmail())
                    .dni(usuarioDTO.getDni())
                    .nombre(usuarioDTO.getNombre())
                    .apellido(usuarioDTO.getApellido())
                    .fecha_nacimiento(usuarioDTO.getFecha_nacimiento())
                    .fecha_creacion(Instant.now())
                    .build();
    
            Set<TipoUsuario> tiposUsuariosCargados = new HashSet<>();
            if (usuarioDTO.getTipoUsuarios() != null) {
                for (TipoUsuario tipoUsuario : usuarioDTO.getTipoUsuarios()) {
                    TipoUsuario tipoUsuarioCargado = tipoUsuarioService.findById(tipoUsuario.getId())
                            .orElseThrow(() -> new RuntimeException("TipoUsuario no encontrado: " + tipoUsuario.getId()));
                    tiposUsuariosCargados.add(tipoUsuarioCargado);
                }
            }
            usuario.setTipoUsuarios(tiposUsuariosCargados);
    
            usuarioService.crearUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado exitosamente");
            
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar el usuario");
        }
    }
}
    
