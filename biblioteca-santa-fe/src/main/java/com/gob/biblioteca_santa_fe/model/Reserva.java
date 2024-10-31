package com.gob.biblioteca_santa_fe.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reserva")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Column(name = "id_hospedaje")
    // private Long id_hospedaje;

    // @Column(name = "id_usuario")
    // private Long id_usuario;

    @ManyToOne
    @JoinColumn(name = "id_hospedaje")
    private Hospedaje hospedaje;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "fecha_check_in")
    private LocalDate fecha_check_in;

    @Column(name = "fecha_check_out")
    private LocalDate fecha_check_out;

    @Column(name = "cant_ninos", precision = 2, scale = 0)
    private Integer cant_ninos;

    @Column(name = "cant_adultos", precision = 2, scale = 0)
    private int cant_adultos;

    @Column(name = "cant_bebes", precision = 2, scale = 0)
    private Integer cant_bebes;

    @Column(name = "cant_mascotas", precision = 2, scale = 0)
    private Integer cant_mascotas;

    @Column(name = "importe_total", precision = 10, scale = 2)
    private BigDecimal importe_total;

    @Column(name = "fecha_creacion")
    private Instant fecha_creacion;

    @Column(name = "fecha_modificacion")
    private Instant fecha_modificacion;

    @Column(name = "estado")
    private String estado;
}