package com.gob.biblioteca_santa_fe.DTOs;

import lombok.Data;

@Data
public class CiudadDTO {
    private Long id;
    private String nombre;
    private PaisDTO pais;
}