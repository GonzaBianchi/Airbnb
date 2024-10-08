package com.gob.biblioteca_santa_fe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gob.biblioteca_santa_fe.model.Usuario;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    public final List<Usuario> usuarios = null;

    // public default Usuario findByUsername(String username) {
    // return usuarios.stream().filter(
    // usuario -> usuario.getUsuario().equals(username))
    // .findAny()
    // .get();

    // }

    public static void agregarUsuario(Usuario usuario) {

        usuarios.add(usuario);
    }
}
