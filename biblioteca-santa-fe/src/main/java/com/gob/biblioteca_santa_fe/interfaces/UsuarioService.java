package com.gob.biblioteca_santa_fe.interfaces;

import com.gob.biblioteca_santa_fe.model.Usuario;

import java.util.List;

public interface UsuarioService {

    public List<Usuario> findAll();

    public Usuario findByUsername(String username);

    public void agregarUsuario(Usuario usuario);
}