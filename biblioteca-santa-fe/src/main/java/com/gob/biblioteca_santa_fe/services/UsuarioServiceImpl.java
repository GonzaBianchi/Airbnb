package com.gob.biblioteca_santa_fe.services;

import com.gob.biblioteca_santa_fe.interfaces.UsuarioService;
import com.gob.biblioteca_santa_fe.model.Usuario;
import com.gob.biblioteca_santa_fe.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario findByUsername(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByUsername'");
    }

    @Override
    public void agregarUsuario(Usuario usuario) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'agregarUsuario'");
    }

    // @Override
    // public Usuario findByUsername(String username) {
    // return UsuarioRepository.findByAutor(username);
    // }

    // @Override
    // public void agregarUsuario(Usuario usuario) {
    // UsuarioRepository.agregarUsuario(usuario);
    // }
}
