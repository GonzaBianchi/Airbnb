package com.gob.biblioteca_santa_fe.model;

import lombok.Data;

import jakarta.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "servicio")
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50)
    private String nombre;

    @ManyToMany(mappedBy = "servicios")
    private Set<Hospedaje> hospedajes;
}