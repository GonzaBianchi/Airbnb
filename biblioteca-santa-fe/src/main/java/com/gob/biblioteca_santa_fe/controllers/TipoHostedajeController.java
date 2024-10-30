package com.gob.biblioteca_santa_fe.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gob.biblioteca_santa_fe.DTOs.TipoHospedajeDTO;
import com.gob.biblioteca_santa_fe.interfaces.TipoHospedajeService;
import com.gob.biblioteca_santa_fe.model.TipoHospedaje;

@PreAuthorize("hasRole('ROLE_ANFITRION')")
@RestController
@RequestMapping("/api/tipoHospedaje")
public class TipoHostedajeController {

    @Autowired
    TipoHospedajeService tipoHospedajeService;

    @PostMapping("/crear")
    public ResponseEntity<TipoHospedaje> crearTipoHospedaje(@RequestBody TipoHospedajeDTO tipoHospedajeDTO) {
    TipoHospedaje tipoHospedajeCreado = tipoHospedajeService.crearTipoHospedaje(tipoHospedajeDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(tipoHospedajeCreado);
}
}
