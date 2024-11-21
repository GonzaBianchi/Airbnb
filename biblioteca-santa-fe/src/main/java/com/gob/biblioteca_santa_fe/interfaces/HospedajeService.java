package com.gob.biblioteca_santa_fe.interfaces;

import java.util.List;
import java.util.Set;

import com.gob.biblioteca_santa_fe.DTOs.HospedajeDTO;
import com.gob.biblioteca_santa_fe.model.Hospedaje;

public interface HospedajeService {
    List<Hospedaje> findByUsuarioId(String jwt);

    Hospedaje crearHospedaje(String jwt, HospedajeDTO HospedajeDTO);

    Hospedaje modificarHospedaje(String jwt, Long id, HospedajeDTO dtoHospedaje);

    Hospedaje eliminarHospedaje(Long id);

    List<Hospedaje> findAll();

    List<Hospedaje> findFiltered(String pais, String ciudad, String tipo, Set<Long> servicioIds);

    Hospedaje findById(Long id);
}
