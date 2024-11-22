package com.gob.biblioteca_santa_fe.DTOs;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Instant;
import java.util.Set;

import com.gob.biblioteca_santa_fe.model.Servicio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HospedajeDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private String imagen;
    private BigDecimal precio_por_noche;
    private Instant fecha_creacion;
    private LocalDateTime fecha_modificacion;
    private Long id_tipo_hospedaje;
    private Long id_ciudad;
    private Set<Servicio> servicios;
    private Long id_usuario;
    private boolean borrado;
}