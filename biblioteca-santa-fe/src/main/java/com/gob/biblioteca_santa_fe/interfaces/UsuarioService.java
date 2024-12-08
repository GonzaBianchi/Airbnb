package com.gob.biblioteca_santa_fe.interfaces;

import com.gob.biblioteca_santa_fe.DTOs.EditarUsuarioDTO;
import com.gob.biblioteca_santa_fe.DTOs.UsuarioDTO;
import com.gob.biblioteca_santa_fe.model.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> findAll();

    Optional<Usuario> findByUsername(String username);

    Optional<Usuario> findById(Long id);

    Usuario registrarUsuario(UsuarioDTO dto);

    void modificarUsuario(EditarUsuarioDTO editarUsuarioDTO, String username);

    Usuario getUsuario(Long id);

    Usuario crearUsuario(Usuario usuario);

    boolean existsByEmail(String email);

}