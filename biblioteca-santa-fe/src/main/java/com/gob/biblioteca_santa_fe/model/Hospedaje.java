package com.gob.biblioteca_santa_fe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Builder
@Entity
@Table(name = "hospedaje")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hospedaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(length = 100)
    private String descripcion;

    @Column(length = 255)
    private String imagen;

    @Column(name = "precio_por_noche", precision = 8, scale = 2)
    private BigDecimal precio_por_noche;

    @Column(name = "fecha_creacion")
    private Instant fecha_creacion;

    @Column(name = "fecha_modificacion")
    private LocalDate fecha_modificacion;

    @ManyToOne
    @JoinColumn(name = "id_tipo_hospedaje")
    private TipoHospedaje id_tipo_hospedaje;

    @ManyToOne
    @JoinColumn(name = "id_ciudad")
    private Ciudad ciudad;

    @OneToMany(mappedBy = "hospedaje", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Reserva> reservas;

    @ManyToMany
    @JoinTable(name = "servicio_hospedaje", joinColumns = @JoinColumn(name = "ID_HOSPEDAJE"), inverseJoinColumns = @JoinColumn(name = "ID_SERVICIO"))
    private Set<Servicio> servicios;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "borrado")
    private boolean borrado;
}
