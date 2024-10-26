package com.gob.biblioteca_santa_fe.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "airbnb_tipo_usuario")
@Getter
@Setter
public class TipoUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

}