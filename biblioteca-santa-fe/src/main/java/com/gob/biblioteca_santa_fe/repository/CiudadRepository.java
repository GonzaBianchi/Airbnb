package com.gob.biblioteca_santa_fe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gob.biblioteca_santa_fe.model.Ciudad;

public interface CiudadRepository extends JpaRepository<Ciudad, Long> {
    
}