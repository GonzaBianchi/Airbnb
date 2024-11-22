package com.gob.biblioteca_santa_fe.services;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.gob.biblioteca_santa_fe.DTOs.ReservaDTO;
import com.gob.biblioteca_santa_fe.interfaces.ReservaService;
import com.gob.biblioteca_santa_fe.model.Hospedaje;
import com.gob.biblioteca_santa_fe.model.Reserva;
import com.gob.biblioteca_santa_fe.model.Usuario;
import com.gob.biblioteca_santa_fe.repository.HospedajeRepository;
import com.gob.biblioteca_santa_fe.repository.ReservaRepository;
import com.gob.biblioteca_santa_fe.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class ReservaServiceImpl implements ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private HospedajeRepository hospedajeRepository;

    @Autowired
    private JwtService jwtService;

    @Override
    public List<Reserva> findAll() {
        try {
            return reservaRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener las reservas: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Reserva crearReserva(ReservaDTO reservaDTO) {
        Hospedaje hospedaje = hospedajeRepository.findById(reservaDTO.getId_hospedaje())
                .orElseThrow(() -> new RuntimeException("Hospedaje no encontrado"));


        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        validarDisponibilidad(hospedaje.getId(), reservaDTO.getFecha_check_in(), reservaDTO.getFecha_check_out(), null);

        BigDecimal importeTotal = calcularImporteTotal(hospedaje, reservaDTO.getFecha_check_in(),
                reservaDTO.getFecha_check_out());

        Reserva reserva = Reserva.builder()

                .hospedaje(hospedaje)
                .usuario(usuario)
                .fecha_check_in(reservaDTO.getFecha_check_in())
                .fecha_check_out(reservaDTO.getFecha_check_out())
                .cant_adultos(reservaDTO.getCant_adultos())
                .cant_ninos(reservaDTO.getCant_ninos())
                .cant_bebes(reservaDTO.getCant_bebes())
                .cant_mascotas(reservaDTO.getCant_mascotas())
                .importe_total(importeTotal)
                .estado("Pendiente")
                .fecha_creacion(Instant.now())
                .build();

        return reservaRepository.save(reserva);
    }

    @Override
    @Transactional
    public Reserva modificarReserva(ReservaDTO reservaDTO, Long idReserva) {
        Reserva reserva = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        LocalDate fechaLimite = reserva.getFecha_check_in().minusDays(1);
        System.out.println("Fecha limite: " + fechaLimite);
        System.out.println("Fecha actual: " + LocalDate.now());
        System.out.println(LocalDate.now().isAfter(fechaLimite));
        if ("Cancelada".equals(reserva.getEstado())) {
            throw new RuntimeException("La reserva ya está cancelada y no puede modificarse");
        }

        // Si la reserva está confirmada, verificar el límite de 24 horas
        if ("Confirmada".equals(reserva.getEstado())) {
            if (LocalDate.now().isAfter(fechaLimite) || LocalDate.now().isEqual(fechaLimite)) {
                throw new RuntimeException(
                        "La reserva confirmada no puede modificarse porque faltan menos de 24 horas para el check-in");
            }
        }

        Hospedaje hospedaje = hospedajeRepository.findById(reservaDTO.getId_hospedaje())
                .orElseThrow(() -> new RuntimeException("Hospedaje no encontrado"));


        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        validarDisponibilidad(hospedaje.getId(), reservaDTO.getFecha_check_in(),
                reservaDTO.getFecha_check_out(), idReserva);

        BigDecimal importeTotal = calcularImporteTotal(hospedaje, reservaDTO.getFecha_check_in(),
                reservaDTO.getFecha_check_out());

        reserva.setHospedaje(hospedaje);
        reserva.setUsuario(usuario);
        reserva.setFecha_check_in(reservaDTO.getFecha_check_in());
        reserva.setFecha_check_out(reservaDTO.getFecha_check_out());
        reserva.setCant_adultos(reservaDTO.getCant_adultos());
        reserva.setCant_ninos(reservaDTO.getCant_ninos());
        reserva.setCant_bebes(reservaDTO.getCant_bebes());
        reserva.setCant_mascotas(reservaDTO.getCant_mascotas());
        reserva.setImporte_total(importeTotal);
        reserva.setFecha_modificacion(Instant.now());

        return reservaRepository.save(reserva);
    }

    @Override
    @Transactional
    public Reserva confirmarReserva(Long idReserva) {

        Reserva reserva = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        System.out.println("estado: " + reserva.getEstado());
        if (!"Pendiente".equals(reserva.getEstado()) || "Cancelada".equals(reserva.getEstado())) {
            throw new IllegalStateException("Solo se pueden confirmar reservas pendientes");
        }

        reserva.setEstado("Confirmada");
        reserva.setFecha_modificacion(Instant.now());

        return reservaRepository.save(reserva);
    }

    @Override
    @Transactional
    public Reserva cancelarReserva(Long idReserva) {
        Reserva reserva = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        if ("Cancelada".equals(reserva.getEstado())) {
            throw new IllegalStateException("La reserva ya está cancelada");
        }

        reserva.setEstado("Cancelada");
        reserva.setFecha_creacion(Instant.now());

        return reservaRepository.save(reserva);
    }

    private void validarDisponibilidad(Long idHospedaje, LocalDate fechaCheckIn, LocalDate fechaCheckOut,
            Long idReservaActual) {
        List<Reserva> reservasExistentes = reservaRepository.findReservasEntreFechas(idHospedaje, fechaCheckIn,
                fechaCheckOut);

        boolean hayConflicto = reservasExistentes.stream()
                .anyMatch(reserva -> idReservaActual == null || !reserva.getId().equals(idReservaActual));

        if (hayConflicto) {
            throw new RuntimeException("El hospedaje no está disponible en las fechas seleccionadas");
        }
    }

    private BigDecimal calcularImporteTotal(Hospedaje hospedaje, LocalDate fechaCheckIn, LocalDate fechaCheckOut) {
        long cantidadNoches = ChronoUnit.DAYS.between(fechaCheckIn, fechaCheckOut);
        return hospedaje.getPrecio_por_noche().multiply(BigDecimal.valueOf(cantidadNoches));
    }

    public List<Reserva> getReservasByUser(String jwt) {
        String username = jwtService.extractUsername(jwt.substring(7));

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        LocalDate today = LocalDate.now();


        List<Reserva> reservas = reservaRepository.findByUsuarioIdAndFechaCheckInGreaterThanEqual(
                usuario.getId(),
                today);
        if (reservas.isEmpty()) {
            throw new RuntimeException("El usuario no tiene hospedajes registrados");
        }

        return reservas;
    }

    @Override
    public List<Reserva> getReservasByHospedajeUser(String jwt) {
        String username = jwtService.extractUsername(jwt.substring(7));

        LocalDate today = LocalDate.now();

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        List<Reserva> reservas = reservaRepository.findReservasByHospedajeUsuarioId(usuario.getId(), today);
        if (reservas.isEmpty()) {
            throw new RuntimeException("El usuario no tiene reservas");
        }

        return reservas;
    }

}
