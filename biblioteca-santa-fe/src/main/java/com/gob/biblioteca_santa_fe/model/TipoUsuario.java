package com.gob.biblioteca_santa_fe.model;

import lombok.Data;
import jakarta.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "airbnb_tipo_usuario")
public class TipoUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50)
    private String nombre;

    @OneToMany(mappedBy = "tipoUsuario")
    private Set<UsuarioTipoUsuario> usuarioTipoUsuarios;
}