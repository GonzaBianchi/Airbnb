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
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<Hospedaje> crearHospedaje(@RequestBody HospedajeDTO hospedajeDTO) {
        System.out.println("hospedaje: "
                + SecurityContextHolder.getContext().getAuthentication().getName());
        try {
            Hospedaje hospedajeCreado = hospedajeService.crearHospedaje(hospedajeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(hospedajeCreado);
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

}
