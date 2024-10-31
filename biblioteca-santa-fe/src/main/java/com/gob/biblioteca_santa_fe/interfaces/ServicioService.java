package com.gob.biblioteca_santa_fe.interfaces;

import com.gob.biblioteca_santa_fe.model.Servicio;
import java.util.List;
import java.util.Optional;

public interface ServicioService {
    List<Servicio> findAll();

    Optional<Servicio> findByNombre(String nombre);

    Servicio crearServicio(Servicio servicio);

    Servicio modificarServicio(Long id, Servicio servicio);

    Servicio borrarServicio(Long id);

    Servicio getServicio(Long id);
}