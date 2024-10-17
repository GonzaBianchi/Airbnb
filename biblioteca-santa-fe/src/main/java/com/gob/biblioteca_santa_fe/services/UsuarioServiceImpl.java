package com.gob.biblioteca_santa_fe.services;

import com.gob.biblioteca_santa_fe.interfaces.UsuarioService;
import com.gob.biblioteca_santa_fe.DTOs.UsuarioDTO;
import com.gob.biblioteca_santa_fe.model.Usuario;
import com.gob.biblioteca_santa_fe.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // @Autowired
    // private PasswordEncoder passwordEncoder;

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Override
    public Usuario registrarUsuario(UsuarioDTO dto) {
        // Validaciones
        if (usuarioRepository.existsByDni(dto.getDni())) {
            throw new RuntimeException("El DNI ya está registrado");
        }
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }
        if (usuarioRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("El nombre de usuario ya está en uso");
        }

        // Crear nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setDni(dto.getDni());
        usuario.setEmail(dto.getEmail());
        usuario.setUsername(dto.getUsername());
        // usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        // usuario.setTipoUsuario(UsuarioTipoUsuario.TipoUsuario.valueOf(dto.getTipoUsuario().toUpperCase()));
        usuario.setFechaCreacion(new Date());
        usuario.setFechaModificacion(new Date());

        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario getUsuario(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public Usuario modificarUsuario(Long id, Usuario usuario) {
        Usuario u = this.getUsuario(id);

        u.setApellido(usuario.getApellido());
        u.setEmail(usuario.getEmail());
        u.setNombre(usuario.getNombre());
        u.setDni(usuario.getDni());
        u.setFechaModificacion(new Date());

        return usuarioRepository.save(u);
    }
}