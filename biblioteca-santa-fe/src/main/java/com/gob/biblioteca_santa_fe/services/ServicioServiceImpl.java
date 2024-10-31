package com.gob.biblioteca_santa_fe.services;

import com.gob.biblioteca_santa_fe.interfaces.ServicioService;
import com.gob.biblioteca_santa_fe.model.Servicio;
import com.gob.biblioteca_santa_fe.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioServiceImpl implements ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    @Override
    public List<Servicio> findAll() {
        return servicioRepository.findAll();
    }

    @Override
    public Optional<Servicio> findByNombre(String nombre) {
        return servicioRepository.findByNombre(nombre);
    }

    @Override
    public Servicio crearServicio(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    @Override
    public Servicio modificarServicio(Long id, Servicio servicio) {
        return servicioRepository.findById(id)
                .map(servicioExistente -> {
                    servicioExistente.setNombre(servicio.getNombre());
                    return servicioRepository.save(servicioExistente);
                })
                .orElse(null);
    }

    @Override
    public Servicio borrarServicio(Long id) {
        return servicioRepository.findById(id)
                .map(servicio -> {
                    servicioRepository.delete(servicio);
                    return servicio;
                })
                .orElse(null);
    }

    @Override
    public Servicio getServicio(Long id) {
        return servicioRepository.findById(id).orElse(null);
    }
}