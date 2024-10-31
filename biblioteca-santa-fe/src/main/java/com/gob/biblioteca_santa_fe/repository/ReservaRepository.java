package com.gob.biblioteca_santa_fe.repository;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gob.biblioteca_santa_fe.model.Reserva;
// import com.gob.biblioteca_santa_fe.model.ReservaId;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
        @Query("SELECT r FROM Reserva r WHERE r.hospedaje.id = :idHospedaje " +
                        "AND ((r.fecha_check_in BETWEEN :fecha_check_in AND :fecha_check_out) " +
                        "OR (r.fecha_check_out BETWEEN :fecha_check_in AND :fecha_check_out)) " +
                        "AND r.estado != 'Cancelada'")
        List<Reserva> findReservasEntreFechas(
                        @Param("idHospedaje") Long idHospedaje,
                        @Param("fecha_check_in") LocalDate fechaCheckIn,
                        @Param("fecha_check_out") LocalDate fechaCheckOut);

        Optional<Reserva> findByHospedajeIdAndUsuarioId(Long hospedajeId, Long usuarioId);

}
