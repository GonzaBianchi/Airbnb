package com.gob.biblioteca_santa_fe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gob.biblioteca_santa_fe.model.Hospedaje;

@Repository
public interface HospedajeRepository extends JpaRepository<Hospedaje, Long> {
        List<Hospedaje> findByUsuarioIdAndBorradoFalse(Long usuarioId);

        List<Hospedaje> findAllByBorradoFalse();

        @Query("SELECT h FROM Hospedaje h " +
                        "JOIN h.ciudad c " +
                        "JOIN c.pais p " +
                        "JOIN h.id_tipo_hospedaje t " +
                        "WHERE (:pais IS NULL OR p.nombre = :pais) " +
                        "AND (:ciudad IS NULL OR c.nombre = :ciudad) " +
                        "AND (:tipo IS NULL OR t.nombre = :tipo)")
        List<Hospedaje> findByBaseFilters(
                        @Param("pais") String pais,
                        @Param("ciudad") String ciudad,
                        @Param("tipo") String tipo);
}
