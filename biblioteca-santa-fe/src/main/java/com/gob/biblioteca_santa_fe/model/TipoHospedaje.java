package com.gob.biblioteca_santa_fe.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "airbnb_tipo_hospedaje")
public class TipoHospedaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50)
    private String nombre;

    @OneToMany(mappedBy = "tipoHospedaje")
    private Set<Hospedaje> hospedajes;
}
