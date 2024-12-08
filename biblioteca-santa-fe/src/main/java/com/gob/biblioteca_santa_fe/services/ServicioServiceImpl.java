package com.gob.biblioteca_santa_fe.services;

import com.gob.biblioteca_santa_fe.interfaces.ServicioService;
import com.gob.biblioteca_santa_fe.model.Servicio;
import com.gob.biblioteca_santa_fe.repository.ServicioRepository;

import jakarta.transaction.Transactional;

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
        return servicioRepository.findByBorradoFalse();
    }

    @Override
    public Optional<Servicio> findByNombre(String nombre) {
        return servicioRepository.findByNombreAndBorradoFalse(nombre);
    }

    @Override
    public Servicio crearServicio(Servicio servicio) {
        Optional<Servicio> existe = servicioRepository.findByNombre(servicio.getNombre());
        if (existe.isPresent()) {
            if (existe.get().isBorrado()) {
                Servicio nuevo = existe.get();
                nuevo.setBorrado(false);
                return servicioRepository.save(nuevo);
            }

            throw new RuntimeException("El servicio ya existe");
        }
        return servicioRepository.save(servicio);
    }

    @Override
    @Transactional
    public Servicio modificarServicio(Long id, Servicio servicio) {
        return servicioRepository.findById(id)
                .map(servicioExistente -> {
                    
                    if (servicioRepository.existsByNombreAndBorradoFalseAndIdNot(servicio.getNombre(), id)) {
                        throw new RuntimeException("Ya existe un servicio activo con este nombre");
                    }

                    
                    Optional<Servicio> servicioMismoNombre = servicioRepository.findByNombre(servicio.getNombre());

                    if (servicioMismoNombre.isPresent() && servicioMismoNombre.get().isBorrado()) {
                        
                        Servicio servicioARestaurar = servicioMismoNombre.get();

                        
                        servicioARestaurar.setBorrado(false);
                        servicioRepository.save(servicioARestaurar);

                     
                        servicioExistente.setBorrado(true);
                        servicioRepository.save(servicioExistente);

                        return servicioARestaurar;
                    }

                    
                    servicioExistente.setNombre(servicio.getNombre());
                    return servicioRepository.save(servicioExistente);
                })
                .orElse(null);
    }

    @Override
    public Servicio borrarServicio(Long id) {
        return servicioRepository.findById(id)
                .map(servicio -> {
                    servicio.setBorrado(true);
                    return servicioRepository.save(servicio);
                })
                .orElse(null);
    }

    @Override
    public Servicio getServicio(Long id) {
        return servicioRepository.findByIdAndBorradoFalse(id).orElse(null);
    }
}