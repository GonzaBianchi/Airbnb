package com.gob.biblioteca_santa_fe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gob.biblioteca_santa_fe.model.Hospedaje;
import com.gob.biblioteca_santa_fe.model.Servicio;

@Repository
public interface HospedajeRepository extends JpaRepository<Hospedaje, Long> {
    List<Hospedaje> findByUsuarioIdAndBorradoFalse(Long usuarioId);

    // Optional<Hospedaje> findByUsuarioIdAndBorradoFalse(Long id, Long usuarioId);
}
