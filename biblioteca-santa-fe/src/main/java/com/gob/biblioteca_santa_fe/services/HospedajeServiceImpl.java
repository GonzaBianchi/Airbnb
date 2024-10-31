package com.gob.biblioteca_santa_fe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gob.biblioteca_santa_fe.DTOs.HospedajeDTO;
import com.gob.biblioteca_santa_fe.interfaces.HospedajeService;
import com.gob.biblioteca_santa_fe.model.Ciudad;
import com.gob.biblioteca_santa_fe.model.Hospedaje;
import com.gob.biblioteca_santa_fe.model.Servicio;
import com.gob.biblioteca_santa_fe.model.TipoHospedaje;
import com.gob.biblioteca_santa_fe.repository.CiudadRepository;
import com.gob.biblioteca_santa_fe.repository.HospedajeRepository;
import com.gob.biblioteca_santa_fe.repository.ServicioRepository;
import com.gob.biblioteca_santa_fe.repository.TipoHospedajeRepository;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;
import java.util.HashSet;

@Service
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
public Hospedaje crearHospedaje(HospedajeDTO hospedajeDTO) {

    Set<Servicio> serviciosCargados = new HashSet<>();
    for (Servicio servicio : hospedajeDTO.getServicios()) {
        Servicio servicioCargado = servicioRepository.findById(servicio.getId())
                .orElseThrow(() -> new RuntimeException("Servicio no dado de alta: " + servicio.getId()));
        serviciosCargados.add(servicioCargado);
    }

    Ciudad ciudadCargada = ciudadRepository.findById(hospedajeDTO.getId_ciudad())
            .orElseThrow(() -> new RuntimeException("Ciudad no encontrada: " + hospedajeDTO.getId_ciudad())
    );

    TipoHospedaje tipoHospedajeCargado = tipoHospedajeRepository.findById(hospedajeDTO.getId_tipo_hospedaje())
            .orElseThrow(() -> new RuntimeException("Tipo de hospedaje no encontrado: " + hospedajeDTO.getId_tipo_hospedaje())
        );

    Hospedaje hospedaje = Hospedaje.builder()
            .descripcion(hospedajeDTO.getDescripcion())
            .imagen(hospedajeDTO.getImagen())
            .precio_por_noche(hospedajeDTO.getPrecioPorNoche())
            .fecha_creacion(Instant.now())
            .ciudad(ciudadCargada) 
            .id_tipo_hospedaje(tipoHospedajeCargado)
            .servicios(serviciosCargados)
            .build();

    return hospedajeRepository.save(hospedaje);
}
    @Override
    public Hospedaje modificarHospedaje(Long id, HospedajeDTO hospedajeDTO) {
    Hospedaje hospedaje = hospedajeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Hospedaje no encontrado: " + id));

    Set<Servicio> serviciosCargados = new HashSet<>();
    for (Servicio servicio : hospedajeDTO.getServicios()) {
        Servicio servicioCargado = servicioRepository.findById(servicio.getId())
                .orElseThrow(() -> new RuntimeException("Servicio no dado de alta: " + servicio.getId()));
        serviciosCargados.add(servicioCargado);
    }

    Ciudad ciudadCargada = ciudadRepository.findById(hospedajeDTO.getId_ciudad())
            .orElseThrow(() -> new RuntimeException("Ciudad no encontrada: " + hospedajeDTO.getId_ciudad()));

    TipoHospedaje tipoHospedajeCargado = tipoHospedajeRepository.findById(hospedajeDTO.getId_tipo_hospedaje())
            .orElseThrow(() -> new RuntimeException("Tipo de hospedaje no encontrado: " + hospedajeDTO.getId_tipo_hospedaje()));

    hospedaje.setDescripcion(hospedajeDTO.getDescripcion());
    hospedaje.setImagen(hospedajeDTO.getImagen());
    hospedaje.setPrecio_por_noche(hospedajeDTO.getPrecioPorNoche());
    hospedaje.setFecha_modificacion(LocalDate.now());
    hospedaje.setCiudad(ciudadCargada);
    hospedaje.setId_tipo_hospedaje(tipoHospedajeCargado);
    hospedaje.setServicios(serviciosCargados);

    return hospedajeRepository.save(hospedaje);
    }

    @Override
    public void eliminarHospedaje(Long id) {
        Hospedaje hospedaje = hospedajeRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Hospedaje no encontrado con el ID: " + id));

        hospedajeRepository.delete(hospedaje);
    }

    public List<Hospedaje> findAll() {
        return hospedajeRepository.findAll();
    }


}
