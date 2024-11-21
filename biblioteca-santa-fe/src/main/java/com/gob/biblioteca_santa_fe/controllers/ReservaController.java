package com.gob.biblioteca_santa_fe.controllers;

import com.gob.biblioteca_santa_fe.DTOs.ReservaDTO;
import com.gob.biblioteca_santa_fe.model.Reserva;
import com.gob.biblioteca_santa_fe.services.ReservaServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaServiceImpl reservaService;

    @PreAuthorize("hasRole('ROLE_INQUILINO')")
    @PostMapping("/crear")
    public ResponseEntity<String> crearReserva(@RequestBody ReservaDTO reservaDTO) {
        try {
            reservaService.crearReserva(reservaDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Reserva creada con éxito.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear la reserva: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_INQUILINO')")
    @PutMapping("/modificar/{idReserva}")
    public ResponseEntity<String> modificarReserva(
            @PathVariable Long idReserva,
            @RequestBody ReservaDTO reservaDTO) {
        try {
            reservaService.modificarReserva(reservaDTO, idReserva);
            return ResponseEntity.ok("Reserva modificada con éxito.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al modificar la reserva: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_INQUILINO', 'ROLE_ANFITRION')")
    @GetMapping("/mis-reservas")
    public ResponseEntity<?> getReservasByUser(@RequestHeader("Authorization") String jwt) {
        try {
            List<Reserva> reservas = reservaService.getReservasByUser(jwt);
            return ResponseEntity.ok(reservas);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener las reservas: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ANFITRION')")
    @PutMapping("/confirmar/{idReserva}")
    public ResponseEntity<?> confirmarReserva(
            @PathVariable Long idReserva) {
        try {
            reservaService.confirmarReserva(idReserva);
            return ResponseEntity.ok("Reserva confirmada con éxito.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al confirmar la reserva: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_INQUILINO', 'ROLE_ANFITRION')")
    @PutMapping("/cancelar/{idReserva}")
    public ResponseEntity<?> cancelarReserva(
            @PathVariable Long idReserva) {
        try {
            reservaService.cancelarReserva(idReserva);
            return ResponseEntity.ok("Reserva cancelada con éxito.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al cancelar la reserva: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ANFITRION')")
    @GetMapping("/ver-reservas")
    public ResponseEntity<?> verReservasAnfitrion(@RequestHeader("Authorization") String jwt) {
        try {
            List<Reserva> reservas = reservaService.getReservasByHospedajeUser(jwt);
            return ResponseEntity.ok(reservas);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener las reservas: " + e.getMessage());
        }
    }

}
