package com.gob.biblioteca_santa_fe.interfaces;

import com.gob.biblioteca_santa_fe.DTOs.ServicioDTO;
import com.gob.biblioteca_santa_fe.model.Servicio;
import java.util.List;
import java.util.Optional;

public interface ServicioService {
    List<Servicio> findAll();

    Optional<Servicio> findByNombre(String nombre);

    Servicio crearServicio(ServicioDTO dto);

    Servicio modificarServicio(Long id, Servicio servicio);

    Servicio getServicio(Long id);

    Servicio borrarServicio(Long id);
}