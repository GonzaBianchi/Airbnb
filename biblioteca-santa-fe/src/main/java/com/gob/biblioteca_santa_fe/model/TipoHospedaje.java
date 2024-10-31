package com.gob.biblioteca_santa_fe.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "tipo_hospedaje")
public class TipoHospedaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String nombre;

    @OneToMany(mappedBy = "id_tipo_hospedaje")
    private Set<Hospedaje> hospedajes;
}
