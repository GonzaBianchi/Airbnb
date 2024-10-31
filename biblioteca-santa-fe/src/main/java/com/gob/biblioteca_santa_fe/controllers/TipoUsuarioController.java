package com.gob.biblioteca_santa_fe.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gob.biblioteca_santa_fe.interfaces.TipoUsuarioService;
import com.gob.biblioteca_santa_fe.model.TipoUsuario;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("api/tipoUsuario")
public class TipoUsuarioController {
    @Autowired
    private TipoUsuarioService tipoUsuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<TipoUsuario> findById(@PathVariable Long id) {
        Optional<TipoUsuario> tipoUsuario = tipoUsuarioService.findById(id);
        return tipoUsuario.map(ResponseEntity::ok) 
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/todosTiposUsuarios")
    public ResponseEntity<List<TipoUsuario>> getAllTipoUsuarios() {
        return ResponseEntity.ok(tipoUsuarioService.findAll());
    }

    @PostMapping("/crear")
    public ResponseEntity<TipoUsuario> crearTipoUsuario(@RequestBody TipoUsuario tipoUsuario) {
        return ResponseEntity.ok(tipoUsuarioService.crearTipoUsuario(tipoUsuario));
    }

    @DeleteMapping("/eliminar/{id}")
    public void deleteTipoUsuario(@PathVariable Long id) {
        tipoUsuarioService.deleteById(id);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<TipoUsuario> editTipoUsuario(@RequestBody TipoUsuario tipoUsuario,
                                                       @PathVariable Long id) {
        return ResponseEntity.ok(tipoUsuarioService.editarTipoUsuario(tipoUsuario, id));
    }
}
