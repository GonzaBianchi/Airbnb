package com.gob.biblioteca_santa_fe.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gob.biblioteca_santa_fe.DTOs.TipoHospedajeDTO;
import com.gob.biblioteca_santa_fe.interfaces.TipoHospedajeService;
import com.gob.biblioteca_santa_fe.model.TipoHospedaje;
import com.gob.biblioteca_santa_fe.repository.TipoHospedajeRepository;

@Service
public class TipoHospedajeServiceImpl implements TipoHospedajeService {
    @Autowired
    private TipoHospedajeRepository tipoHospedajeRepository;


    @Override
    public TipoHospedaje crearTipoHospedaje(TipoHospedajeDTO tipoHospedajeDTO) {
    TipoHospedaje tipoHospedaje = new TipoHospedaje();
     tipoHospedaje.setNombre(tipoHospedajeDTO.getNombre());
    
    return tipoHospedajeRepository.save(tipoHospedaje);
}
    
}
