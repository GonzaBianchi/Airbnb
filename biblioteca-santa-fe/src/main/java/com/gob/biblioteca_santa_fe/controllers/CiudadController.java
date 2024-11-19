package com.gob.biblioteca_santa_fe.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gob.biblioteca_santa_fe.model.Ciudad;
import com.gob.biblioteca_santa_fe.services.CiudadServiceImpl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@PreAuthorize("permitAll")
@RestController
@RequestMapping("/api/ciudad")
public class CiudadController {

    @Autowired
    CiudadServiceImpl ciudadService;

    @GetMapping("/{idPais}")
    public ResponseEntity<List<Ciudad>> findByIdPais(
            @PathVariable Long idPais) {
        List<Ciudad> ciudad = ciudadService.findByIdPais(idPais);
        return ResponseEntity.ok(ciudad);
    }

}
