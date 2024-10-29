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
import org.springframework.util.StringUtils;
import java.time.Instant;
import java.time.LocalDate;
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
    public Usuario editarUsuario(Usuario usuario, Long id) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);

        if (optionalUsuario.isPresent()) {
            Usuario usuarioEditar = optionalUsuario.get();
            if (!StringUtils.hasText(usuario.getEmail())
                    || !usuario.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                throw new RuntimeException("Invalid email format.");
            }
            Optional<Usuario> existingUser = usuarioRepository.findByEmail(usuario.getEmail());
            if (existingUser.isPresent() && !existingUser.get().getId().equals(id)) {
                throw new RuntimeException("Email already in use.");
            }
            usuarioEditar.setUsername(usuario.getUsername());
            usuarioEditar.setPassword(passwordEncoder.encode(usuario.getPassword()));
            usuarioEditar.setEmail(usuario.getEmail());
            usuarioEditar.setNombre(usuario.getNombre());
            usuarioEditar.setApellido(usuario.getApellido());
            usuarioEditar.setFecha_nacimiento(usuario.getFecha_nacimiento());
            usuarioEditar.setFecha_modificacion(LocalDate.from(Instant.now()));
            return usuarioRepository.save(usuarioEditar);
        } else {
            throw new RuntimeException("ID del usuario no encontrado: " + id);
        }
    }

    @Override
    public Usuario modificarUsuario(Long id, Usuario usuario) {
        return null;
    }

    @Override
    public void actualizarUsuario(EditarUsuarioDTO editarUsuarioDTO, String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("No existe el usuario " + username));

        usuario.setNombre(editarUsuarioDTO.getNombre());
        usuario.setApellido(editarUsuarioDTO.getApellido());
        usuario.setEmail(editarUsuarioDTO.getEmail());

        usuarioRepository.save(usuario);
    }
}