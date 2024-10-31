package com.gob.biblioteca_santa_fe.interfaces;

import com.gob.biblioteca_santa_fe.DTOs.HospedajeDTO;
import com.gob.biblioteca_santa_fe.model.Hospedaje;

public interface HospedajeService {

    Hospedaje crearHospedaje(HospedajeDTO HospedajeDTO);

    Hospedaje modificarHospedaje(Long id, HospedajeDTO dtoHospedaje);

    void eliminarHospedaje(Long id);
}
