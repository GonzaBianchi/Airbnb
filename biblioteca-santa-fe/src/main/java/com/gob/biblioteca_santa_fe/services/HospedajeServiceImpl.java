package com.gob.biblioteca_santa_fe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gob.biblioteca_santa_fe.DTOs.HospedajeDTO;
import com.gob.biblioteca_santa_fe.interfaces.HospedajeService;
import com.gob.biblioteca_santa_fe.model.Hospedaje;
import com.gob.biblioteca_santa_fe.repository.CiudadRepository;
import com.gob.biblioteca_santa_fe.repository.HospedajeRepository;
import com.gob.biblioteca_santa_fe.repository.ServicioRepository;
import com.gob.biblioteca_santa_fe.repository.TipoHospedajeRepository;

public class HospedajeServiceImpl implements HospedajeService {
    @Autowired
    private HospedajeRepository hospedajeRepository;

    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private TipoHospedajeRepository tipoHospedajeRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Override
    public Hospedaje crearHospedaje(Hospedaje hospedaje) {
        return hospedajeRepository.save(hospedaje);
    }

    @Override
    public Hospedaje modificarHospedaje(HospedajeDTO dtoHospedaje) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modificarHospedaje'");
    }

    @Override
    public Hospedaje eliminarHospedaje(HospedajeDTO dtoHospedaje) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminarHospedaje'");
    }

    public List<Hospedaje> findAll() {
        return hospedajeRepository.findAll();
    }

}
