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

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    @PostMapping("/crear")
    public ResponseEntity<String> crearHospedaje(@RequestBody HospedajeDTO hospedajeDTO) {
        System.out.println("hospedaje: "
                + SecurityContextHolder.getContext().getAuthentication().getName());
        try {
            hospedajeService.crearHospedaje(hospedajeDTO);
            return ResponseEntity.status(HttpStatus.OK).body("hospedaje creado correctamente");
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
    @PreAuthorize("hasRole('ROLE_ANFITRION')")
    @PutMapping("/modificar/{id}")
    public ResponseEntity<String> modificarHospedaje(@PathVariable Long id, @RequestBody HospedajeDTO hospedajeDTO) {
    try {
        hospedajeService.modificarHospedaje(id, hospedajeDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Hospedaje modificado correctamente");
    } catch (RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
    @PreAuthorize("hasRole('ROLE_ANFITRION')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarHospedaje(@PathVariable Long id) {
        try {
            hospedajeService.eliminarHospedaje(id);
            return ResponseEntity.status(HttpStatus.OK).body("Hospedaje eliminado correctamente");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
}
