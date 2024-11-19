package com.gob.biblioteca_santa_fe.interfaces;

import java.util.List;

import com.gob.biblioteca_santa_fe.DTOs.HospedajeDTO;
import com.gob.biblioteca_santa_fe.model.Hospedaje;

public interface HospedajeService {
    List<Hospedaje> findByUsuarioId(String jwt);

    Hospedaje crearHospedaje(String jwt, HospedajeDTO HospedajeDTO);

    Hospedaje modificarHospedaje(String jwt, Long id, HospedajeDTO dtoHospedaje);

    Hospedaje eliminarHospedaje(Long id);
}
