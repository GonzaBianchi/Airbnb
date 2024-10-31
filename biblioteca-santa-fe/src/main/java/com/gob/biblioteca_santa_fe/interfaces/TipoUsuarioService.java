package com.gob.biblioteca_santa_fe.interfaces;

import java.util.List;
import java.util.Optional;

import com.gob.biblioteca_santa_fe.model.TipoUsuario;

public interface TipoUsuarioService {

    Optional<TipoUsuario> findById(Long id);

    List<TipoUsuario> findAll();

    void deleteById(Long id);

    TipoUsuario crearTipoUsuario(TipoUsuario tipoUsuario);

    TipoUsuario editarTipoUsuario(TipoUsuario tipoUsuario, Long id);
}
