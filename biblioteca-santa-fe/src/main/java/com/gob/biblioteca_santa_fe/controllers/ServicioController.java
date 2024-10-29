package com.gob.biblioteca_santa_fe.controllers;

import java.util.List;
import java.util.Optional;

// import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gob.biblioteca_santa_fe.DTOs.ServicioDTO;
// import com.gob.biblioteca_santa_fe.interfaces.ServicioService;
import com.gob.biblioteca_santa_fe.model.Servicio;
import com.gob.biblioteca_santa_fe.services.ServicioServiceImpl;

@RestController
@RequestMapping("/api/servicios")
public class ServicioController {
    @Autowired
    private ServicioServiceImpl servicioService;

    @GetMapping()
    public ResponseEntity<List<Servicio>> findAll() {
        List<Servicio> servicios = servicioService.findAll();
        return ResponseEntity.ok(servicios);
    }

    @GetMapping("/{nombre}")
    public ResponseEntity<Optional<Servicio>> findByNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(servicioService.findByNombre(nombre));
    }

    @PostMapping()
    public Servicio crearServicio(@RequestBody ServicioDTO servicioDTO) {
        Servicio servicio = Servicio.builder()
                .nombre(servicioDTO.getNombre())
                .build();

        return servicioService.crearServicio(servicio);
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> modificarServicio(@RequestBody Servicio servicio) {
        return ResponseEntity.ok("El servicio se modifico con exito");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> borrarServicio(@RequestBody Servicio servicio) {
        return ResponseEntity.ok("El servicio se borró con exito");
    }
}
