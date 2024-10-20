package com.gob.biblioteca_santa_fe.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "airbnb_usuario_tipo_usuario")
public class UsuarioTipoUsuario {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_tipo_usuario")
    private TipoUsuario tipoUsuario;
}
