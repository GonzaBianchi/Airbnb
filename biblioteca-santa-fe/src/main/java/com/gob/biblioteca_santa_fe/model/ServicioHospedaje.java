package com.gob.biblioteca_santa_fe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Table(name = "servicio_hospedaje")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServicioHospedaje {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_servicio")
    private Servicio servicio;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_hospedaje")
    private Hospedaje hospedaje;
}