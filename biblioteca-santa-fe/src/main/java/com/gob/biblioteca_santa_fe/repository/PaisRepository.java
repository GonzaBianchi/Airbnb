package com.gob.biblioteca_santa_fe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gob.biblioteca_santa_fe.model.Pais;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Long> {
}