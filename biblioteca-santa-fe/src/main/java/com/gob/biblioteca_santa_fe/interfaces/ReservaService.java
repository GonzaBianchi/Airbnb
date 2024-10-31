package com.gob.biblioteca_santa_fe.interfaces;

import java.util.List;

import com.gob.biblioteca_santa_fe.DTOs.ReservaDTO;
import com.gob.biblioteca_santa_fe.model.Reserva;

public interface ReservaService {
    List<Reserva> findAll() throws RuntimeException;

    Reserva crearReserva(ReservaDTO reservaDTO) throws RuntimeException;

    Reserva modificarReserva(ReservaDTO reservaDTO, Long idReserva) throws RuntimeException;

    Reserva confirmarReserva(Long idReserva) throws RuntimeException;

    Reserva cancelarReserva(Long idReserva) throws RuntimeException;
}
