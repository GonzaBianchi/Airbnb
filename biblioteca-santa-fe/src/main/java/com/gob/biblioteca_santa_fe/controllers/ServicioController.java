package com.gob.biblioteca_santa_fe.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gob.biblioteca_santa_fe.DTOs.ServicioDTO;
import com.gob.biblioteca_santa_fe.model.Servicio;
import com.gob.biblioteca_santa_fe.services.ServicioServiceImpl;

@RestController
@RequestMapping("/api/servicios")
public class ServicioController {
    @Autowired
    private ServicioServiceImpl servicioService;

    @PreAuthorize("permitAll")
    @GetMapping()
    public ResponseEntity<List<Servicio>> findAll() {
        System.out.println("Accessing /api/servicios GET endpoint");
        List<Servicio> servicios = servicioService.findAll();
        return ResponseEntity.ok(servicios);
    }

    @PreAuthorize("permitAll")
    @GetMapping("/{nombre}")
    public ResponseEntity<Optional<Servicio>> findByNombre(@PathVariable String nombre) {
        System.out.println("Accessing /api/servicios/{nombre} GET endpoint");
        return ResponseEntity.ok(servicioService.findByNombre(nombre));
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    @PostMapping("/crear")
    public ResponseEntity<Servicio> crearServicio(@RequestBody ServicioDTO servicioDTO) {
        System.out.println("Accessing /api/servicios POST endpoint");
        System.out.println("ServicioDTO crear: " + servicioDTO);
        Servicio servicio = Servicio.builder()
                .nombre(servicioDTO.getNombre())
                .build();

        System.out.println("Attempting to create new service with user: "
                + SecurityContextHolder.getContext().getAuthentication().getName());
        try {
            Servicio servicioCreado = servicioService.crearServicio(servicio);
            return ResponseEntity.status(HttpStatus.CREATED).body(servicioCreado);
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> modificarServicio(@PathVariable Long id, @RequestBody ServicioDTO servicioDTO) {
        System.out.println("Accessing /api/servicios/{id} PUT endpoint");
        try {
            Servicio servicio = Servicio.builder()
                    .nombre(servicioDTO.getNombre())
                    .build();

            Servicio servicioModificado = servicioService.modificarServicio(id, servicio);

            if (servicioModificado != null) {
                return ResponseEntity.ok(servicioModificado);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al modificar el servicio: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    @PutMapping("/borrar/{id}")
    public ResponseEntity<?> borrarServicio(@PathVariable Long id) {
        System.out.println("Accessing /api/servicios/{id} DELETE endpoint");
        try {
            Servicio servicioEliminado = servicioService.borrarServicio(id);

            if (servicioEliminado.isBorrado()) {
                return ResponseEntity.ok("Servicio eliminado con éxito");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el servicio: " + e.getMessage());
        }
    }
}