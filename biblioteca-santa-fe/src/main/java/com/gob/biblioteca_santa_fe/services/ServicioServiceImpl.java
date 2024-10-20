package com.gob.biblioteca_santa_fe.services;

import com.gob.biblioteca_santa_fe.interfaces.ServicioService;
import com.gob.biblioteca_santa_fe.DTOs.ServicioDTO;
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
    public Servicio crearServicio(ServicioDTO dto) {
        if (servicioRepository.existsByNombre(dto.getNombre())) {
            throw new RuntimeException("El nombre de servicio ya está en uso");
        }

        // Crear nuevo servicio
        Servicio servicio = new Servicio();
        servicio.setNombre(dto.getNombre());

        return servicioRepository.save(servicio);
    }

    @Override
    public Servicio modificarServicio(Long id, Servicio servicio) {
        Servicio s = this.getServicio(id);

        s.setNombre(servicio.getNombre());

        return servicioRepository.save(s);
    }

    @Override
    public Servicio borrarServicio(Long id) {
        Servicio s = this.getServicio(id);
        if (s != null) {
            servicioRepository.delete(s);
        }
        return s;
    }

    @Override
    public Servicio getServicio(Long id) {
        return servicioRepository.findById(id).orElse(null);
    }
}