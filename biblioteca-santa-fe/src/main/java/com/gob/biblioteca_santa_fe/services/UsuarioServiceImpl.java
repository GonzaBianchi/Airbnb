package com.gob.biblioteca_santa_fe.services;

import com.gob.biblioteca_santa_fe.interfaces.UsuarioService;
import com.gob.biblioteca_santa_fe.DTOs.EditarUsuarioDTO;
import com.gob.biblioteca_santa_fe.DTOs.UsuarioDTO;
import com.gob.biblioteca_santa_fe.model.TipoUsuario;
import com.gob.biblioteca_santa_fe.model.Usuario;
import com.gob.biblioteca_santa_fe.repository.TipoUsuarioRepository;
import com.gob.biblioteca_santa_fe.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.Instant;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario registrarUsuario(UsuarioDTO dto) {
        return null;
    }

    @Override
    public Usuario getUsuario(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public Usuario crearUsuario(Usuario usuario) {

        Set<TipoUsuario> tiposUsuariosCargados = new HashSet<>();

        for (TipoUsuario tipoUsuario : usuario.getTipoUsuarios()) {
            TipoUsuario tipoUsuarioCargado = tipoUsuarioRepository.findById(tipoUsuario.getId())
                    .orElseThrow(() -> new RuntimeException("TipoUsuario no encontrado: " + tipoUsuario.getId()));
            tiposUsuariosCargados.add(tipoUsuarioCargado);
        }

        usuario.setTipoUsuarios(tiposUsuariosCargados);

        return usuarioRepository.save(usuario);
    }

    @Override
    public void modificarUsuario(EditarUsuarioDTO editarUsuarioDTO, String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("No existe el usuario " + username));

        usuario.setNombre(editarUsuarioDTO.getNombre());
        usuario.setApellido(editarUsuarioDTO.getApellido());
        usuario.setEmail(editarUsuarioDTO.getEmail());

        // Solo actualizar la contraseña si se proporciona una nueva
        if (editarUsuarioDTO.getPassword() != null && !editarUsuarioDTO.getPassword().trim().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(editarUsuarioDTO.getPassword()));
        }
        // Si no se proporciona contraseña, se mantiene la existente

        usuario.setFecha_nacimiento(editarUsuarioDTO.getFecha_nacimiento());
        usuario.setFecha_modificacion(Instant.now());
        usuario.setDni(editarUsuarioDTO.getDni());
        usuario.setUsername(editarUsuarioDTO.getUsername());

        usuarioRepository.save(usuario);
    }

    @Override
    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }
}