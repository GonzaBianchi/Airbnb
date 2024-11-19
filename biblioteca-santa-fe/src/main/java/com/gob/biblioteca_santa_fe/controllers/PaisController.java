package com.gob.biblioteca_santa_fe.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gob.biblioteca_santa_fe.model.Pais;
import com.gob.biblioteca_santa_fe.services.PaisServiceImpl;

import org.springframework.web.bind.annotation.GetMapping;

@PreAuthorize("permitAll")
@RestController
@RequestMapping("/api/pais")
public class PaisController {

    @Autowired
    PaisServiceImpl paisService;

    @GetMapping("/")
    public ResponseEntity<List<Pais>> findAll() {
        List<Pais> pais = paisService.findAll();
        return ResponseEntity.ok(pais);
    }

}
