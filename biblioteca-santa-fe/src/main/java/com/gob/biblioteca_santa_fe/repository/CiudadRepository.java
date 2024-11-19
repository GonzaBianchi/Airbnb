package com.gob.biblioteca_santa_fe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gob.biblioteca_santa_fe.model.Ciudad;

@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, Long> {
    List<Ciudad> findByPaisId(Long paisId);
}