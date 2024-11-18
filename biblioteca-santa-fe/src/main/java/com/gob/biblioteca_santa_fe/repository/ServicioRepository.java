package com.gob.biblioteca_santa_fe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gob.biblioteca_santa_fe.model.Servicio;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {
    Optional<Servicio> findByNombre(String nombre);

    boolean existsByNombre(String nombre);

    List<Servicio> findByBorradoFalse();

    Optional<Servicio> findByNombreAndBorradoFalse(String nombre);

    Optional<Servicio> findByIdAndBorradoFalse(Long id);

    boolean existsByNombreAndBorradoFalse(String nombre);

    boolean existsByNombreAndBorradoFalseAndIdNot(String nombre, Long id);
}