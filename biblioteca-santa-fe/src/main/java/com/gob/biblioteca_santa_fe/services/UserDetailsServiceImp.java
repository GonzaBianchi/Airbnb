package com.gob.biblioteca_santa_fe.services;

import com.gob.biblioteca_santa_fe.DTOs.UsuarioDTO;
import com.gob.biblioteca_santa_fe.model.Usuario;
import com.gob.biblioteca_santa_fe.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Set;
import java.util.stream.Collectors;
import java.time.Instant;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario registrOneCustomer(UsuarioDTO usuarioDTO) {
        System.out.println("usuarioDTO registro: " + usuarioDTO);
        validatePassword(usuarioDTO);

        Usuario usuario = new Usuario();
        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword())); // Encriptar la contraseña
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setTipoUsuarios(usuarioDTO.getTipoUsuarios());
        usuario.setFecha_nacimiento(usuarioDTO.getFecha_nacimiento());
        usuario.setFecha_creacion(Instant.now());

        return usuarioRepository.save(usuario);
    }

    // valida contraseña
    private void validatePassword(UsuarioDTO usuarioDTO) {
        if (usuarioDTO.getPassword().length() < 8) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres.");
        }

    }

    // TipoUsuarios a authorities y aignar esas authorities al usuario
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("load username: " + username);
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el nombre: " + username));
        System.out.println("usuario cargado: " + usuario);

        Set<GrantedAuthority> authorities = usuario.getTipoUsuarios().stream()
                .map(tipoUsuario -> new SimpleGrantedAuthority(tipoUsuario.getNombre()))
                .collect(Collectors.toSet());
        System.out.println("autoridades para usuario: " + username + ":" + authorities);

        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities(authorities)
                .accountExpired(false)
                .credentialsExpired(false)
                .accountLocked(false)
                .disabled(!usuario.isEnabled())
                .build();
    }
}
