package com.gob.biblioteca_santa_fe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

@Data
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

    @Column(length = 100)
    private String descripcion;

    @Column(length = 255)
    private String imagen;

    @Column(name = "precio_por_noche", precision = 8, scale = 2)
    private BigDecimal precioPorNoche;

    @Column(name = "fecha_creacion")
    private Instant fechaCreacion;

    @Column(name = "fecha_modificacion")
    private Instant fechaModificacion;

    @ManyToOne
    @JoinColumn(name = "id_tipo_hospedaje")
    private TipoHospedaje tipoHospedaje;

    @ManyToOne
    @JoinColumn(name = "ID_CIUDAD")
    private Ciudad ciudad;

    @OneToMany(mappedBy = "hospedaje")
    private Set<Reserva> reservas;

    @ManyToMany
    @JoinTable(name = "servicio_hospedaje", joinColumns = @JoinColumn(name = "ID_HOSPEDAJE"), inverseJoinColumns = @JoinColumn(name = "ID_SERVICIO"))
    private Set<Servicio> servicios;
}
