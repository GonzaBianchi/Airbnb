package com.gob.biblioteca_santa_fe.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gob.biblioteca_santa_fe.DTOs.HospedajeDTO;
import com.gob.biblioteca_santa_fe.model.Hospedaje;
import com.gob.biblioteca_santa_fe.services.HospedajeServiceImpl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/api/hospedajes")
public class HospedajeController {
    @Autowired
    private HospedajeServiceImpl hospedajeService;

    @PreAuthorize("permitAll")
    @GetMapping()
    public ResponseEntity<List<Hospedaje>> findAll() {
        List<Hospedaje> hospedajes = hospedajeService.findAll();
        return ResponseEntity.ok(hospedajes);
    }

    @PreAuthorize("hasRole('ROLE_ANFITRION')")
    @GetMapping("/mis-hospedajes")
    public ResponseEntity<?> getHospedajesByUsuario(@RequestHeader("Authorization") String jwt) {
        try {
            List<Hospedaje> hospedajes = hospedajeService.findByUsuarioId(jwt);
            return ResponseEntity.ok(hospedajes);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ANFITRION')")
    @PostMapping("/crear")
    public ResponseEntity<String> crearHospedaje(
            @RequestHeader("Authorization") String jwt,
            @RequestBody HospedajeDTO hospedajeDTO) {
        System.out.println("hospedaje: "
                + SecurityContextHolder.getContext().getAuthentication().getName());
        try {
            hospedajeService.crearHospedaje(jwt, hospedajeDTO);
            return ResponseEntity.status(HttpStatus.OK).body("hospedaje creado correctamente");
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ANFITRION')")
    @PutMapping("/modificar/{id}")
    public ResponseEntity<String> modificarHospedaje(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id,
            @RequestBody HospedajeDTO hospedajeDTO) {
        try {
            hospedajeService.modificarHospedaje(jwt, id, hospedajeDTO);
            return ResponseEntity.status(HttpStatus.OK).body("Hospedaje modificado correctamente");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ANFITRION')")
    @PutMapping("/eliminar/{id}")
    public ResponseEntity<Hospedaje> eliminarHospedaje(@PathVariable Long id) {
        try {
            Hospedaje hospedajeEliminado = hospedajeService.eliminarHospedaje(id);
            return ResponseEntity.ok(hospedajeEliminado);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
