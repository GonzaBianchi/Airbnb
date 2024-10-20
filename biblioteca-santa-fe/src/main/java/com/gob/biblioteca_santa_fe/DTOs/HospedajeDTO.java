package com.gob.biblioteca_santa_fe.DTOs;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
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