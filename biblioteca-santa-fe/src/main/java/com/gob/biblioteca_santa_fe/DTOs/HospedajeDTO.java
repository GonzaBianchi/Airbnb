package com.gob.biblioteca_santa_fe.DTOs;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

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
    private String descripcion;
    private String imagen;
    private BigDecimal precioPorNoche;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    private Long idTipoHospedaje;
    private Long idCiudad;
    private Set<Long> idServicios;
}