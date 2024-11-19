package com.gob.biblioteca_santa_fe.interfaces;

import java.util.List;

import com.gob.biblioteca_santa_fe.DTOs.TipoHospedajeDTO;
import com.gob.biblioteca_santa_fe.model.TipoHospedaje;

public interface TipoHospedajeService {

    TipoHospedaje crearTipoHospedaje(TipoHospedajeDTO tipoHospedajeDTO);

    List<TipoHospedaje> findAll();
}
