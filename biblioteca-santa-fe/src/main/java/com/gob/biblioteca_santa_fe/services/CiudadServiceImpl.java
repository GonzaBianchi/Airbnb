package com.gob.biblioteca_santa_fe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gob.biblioteca_santa_fe.interfaces.CiudadService;
import com.gob.biblioteca_santa_fe.model.Ciudad;
import com.gob.biblioteca_santa_fe.repository.CiudadRepository;

@Service
public class CiudadServiceImpl implements CiudadService {
    @Autowired
    private CiudadRepository ciudadRepository;

    @Override
    public List<Ciudad> findByIdPais(Long paisId) {
        return ciudadRepository.findByPaisId(paisId);
    }
}
