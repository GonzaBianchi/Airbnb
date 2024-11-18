package com.gob.biblioteca_santa_fe.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
// import java.util.Set;

@Data
@Builder
@Entity
@Table(name = "servicio")
@NoArgsConstructor
@AllArgsConstructor
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String nombre;

    @Column(name = "borrado")
    private boolean borrado;
}