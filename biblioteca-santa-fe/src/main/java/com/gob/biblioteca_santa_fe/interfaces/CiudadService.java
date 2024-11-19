package com.gob.biblioteca_santa_fe.interfaces;

import java.util.List;

import com.gob.biblioteca_santa_fe.model.Ciudad;

public interface CiudadService {

    List<Ciudad> findByIdPais(Long paisId);
}
