package com.gob.biblioteca_santa_fe.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "airbnb_reserva")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_hospedaje")
    private Hospedaje hospedaje;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "fecha_check_in")
    private LocalDate fechaCheckIn;

    @Column(name = "fecha_check_out")
    private LocalDate fechaCheckOut;

    @Column(name = "cant_ninos", precision = 2, scale = 0)
    private BigDecimal cantNinos;

    @Column(name = "cant_adultos", precision = 2, scale = 0)
    private BigDecimal cantAdultos;

    @Column(name = "cant_bebes", precision = 2, scale = 0)
    private BigDecimal cantBebes;

    @Column(name = "cant_mascotas", precision = 2, scale = 0)
    private BigDecimal cantMascotas;

    @Column(name = "importe_total", precision = 10, scale = 2)
    private BigDecimal importeTotal;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;
}
