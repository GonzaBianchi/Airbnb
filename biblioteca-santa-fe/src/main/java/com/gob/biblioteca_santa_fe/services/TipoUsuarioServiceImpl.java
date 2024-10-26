package com.gob.biblioteca_santa_fe.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gob.biblioteca_santa_fe.interfaces.TipoUsuarioService;
import com.gob.biblioteca_santa_fe.model.TipoUsuario;
import com.gob.biblioteca_santa_fe.repository.TipoUsuarioRepository;

@Service
public class TipoUsuarioServiceImpl implements TipoUsuarioService{

    @Autowired
    private TipoUsuarioRepository tipoUsuario;
    @Override
    public Optional<TipoUsuario> findById(Long id) {
        return tipoUsuario.findById(id);
    }

    @Override
    public List<TipoUsuario> findAll() {
        return tipoUsuario.findAll();
    }

    @Override
    public void deleteById(Long id) {
        tipoUsuario.deleteById(id);
    }

    @Override
    public TipoUsuario crearTipoUsuario(TipoUsuario tipoUsuarioModel) {
        return tipoUsuario.save(tipoUsuarioModel);
    }

    @Override
    public TipoUsuario editarTipoUsuario(TipoUsuario tipoUsuarioModel, Long id) {
        Optional<TipoUsuario> optionalTipo = tipoUsuario.findById(id);

        if (optionalTipo.isPresent()) {
            TipoUsuario user = optionalTipo.get();

            user.setNombre(tipoUsuarioModel.getNombre());
            return tipoUsuario.save(user);
        }else {
            throw new RuntimeException("Tipo de Usuario no encontrado con el ID: " + id);
        }
    }
    
}
