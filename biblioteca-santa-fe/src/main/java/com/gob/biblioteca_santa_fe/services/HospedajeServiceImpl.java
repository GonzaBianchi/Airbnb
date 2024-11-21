package com.gob.biblioteca_santa_fe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gob.biblioteca_santa_fe.DTOs.HospedajeDTO;
import com.gob.biblioteca_santa_fe.interfaces.HospedajeService;
import com.gob.biblioteca_santa_fe.model.Ciudad;
import com.gob.biblioteca_santa_fe.model.Hospedaje;
import com.gob.biblioteca_santa_fe.model.Servicio;
import com.gob.biblioteca_santa_fe.model.TipoHospedaje;
import com.gob.biblioteca_santa_fe.model.Usuario;
import com.gob.biblioteca_santa_fe.repository.CiudadRepository;
import com.gob.biblioteca_santa_fe.repository.HospedajeRepository;
import com.gob.biblioteca_santa_fe.repository.ServicioRepository;
import com.gob.biblioteca_santa_fe.repository.TipoHospedajeRepository;
import com.gob.biblioteca_santa_fe.repository.UsuarioRepository;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.HashSet;

@Service
public class HospedajeServiceImpl implements HospedajeService {
        @Autowired
        private HospedajeRepository hospedajeRepository;

        @Autowired
        private CiudadRepository ciudadRepository;

        @Autowired
        private TipoHospedajeRepository tipoHospedajeRepository;

        @Autowired
        private ServicioRepository servicioRepository;

        @Autowired
        private UsuarioRepository usuarioRepository;

        @Autowired
        private JwtService jwtService;

        @Override
        public List<Hospedaje> findByUsuarioId(String jwt) {
                String username = jwtService.extractUsername(jwt.substring(7)); // Remove "Bearer "

                // Find user by username
                Usuario usuario = usuarioRepository.findByUsername(username)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                List<Hospedaje> hospedajes = hospedajeRepository.findByUsuarioIdAndBorradoFalse(usuario.getId());

                if (hospedajes.isEmpty()) {
                        throw new RuntimeException("El usuario no tiene hospedajes registrados");
                }

                return hospedajes;
        }

        @Override
        public Hospedaje crearHospedaje(String jwt, HospedajeDTO hospedajeDTO) {

                String username = jwtService.extractUsername(jwt.substring(7));

                Usuario usuario = usuarioRepository.findByUsername(username)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                hospedajeDTO.setId_usuario(usuario.getId());
                System.out.println("Id usuario creador del hospedaje: " + hospedajeDTO.getId_usuario());

                Set<Servicio> serviciosCargados = new HashSet<>();
                for (Servicio servicio : hospedajeDTO.getServicios()) {
                        Servicio servicioCargado = servicioRepository.findById(servicio.getId())
                                        .orElseThrow(() -> new RuntimeException(
                                                        "Servicio no dado de alta: " + servicio.getId()));
                        serviciosCargados.add(servicioCargado);
                }

                Ciudad ciudadCargada = ciudadRepository.findById(hospedajeDTO.getId_ciudad())
                                .orElseThrow(() -> new RuntimeException(
                                                "Ciudad no encontrada: " + hospedajeDTO.getId_ciudad()));

                TipoHospedaje tipoHospedajeCargado = tipoHospedajeRepository
                                .findById(hospedajeDTO.getId_tipo_hospedaje())
                                .orElseThrow(() -> new RuntimeException(
                                                "Tipo de hospedaje no encontrado: "
                                                                + hospedajeDTO.getId_tipo_hospedaje()));

                Hospedaje hospedaje = Hospedaje.builder()
                                .descripcion(hospedajeDTO.getDescripcion())
                                .imagen(hospedajeDTO.getImagen())
                                .precio_por_noche(hospedajeDTO.getPrecio_por_noche())
                                .fecha_creacion(Instant.now())
                                .ciudad(ciudadCargada)
                                .id_tipo_hospedaje(tipoHospedajeCargado)
                                .servicios(serviciosCargados)
                                .usuario(usuario)
                                .build();
                System.out.println("DATOS DE HOSPEDAJE A CREAR: " + hospedaje);
                return hospedajeRepository.save(hospedaje);
        }

        @Override
        public Hospedaje modificarHospedaje(String jwt, Long id, HospedajeDTO hospedajeDTO) {
                String username = jwtService.extractUsername(jwt.substring(7)); // Remove "Bearer "

                // Find user by username
                Usuario usuario = usuarioRepository.findByUsername(username)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                // Verify the hospedaje belongs to the user
                Hospedaje hospedaje = hospedajeRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Hospedaje no encontrado"));

                if (!hospedaje.getUsuario().getId().equals(usuario.getId())) {
                        throw new RuntimeException("No tiene permisos para modificar este hospedaje");
                }

                Set<Servicio> serviciosCargados = new HashSet<>();
                for (Servicio servicio : hospedajeDTO.getServicios()) {
                        Servicio servicioCargado = servicioRepository.findById(servicio.getId())
                                        .orElseThrow(() -> new RuntimeException(
                                                        "Servicio no dado de alta: " + servicio.getId()));
                        serviciosCargados.add(servicioCargado);
                }

                Ciudad ciudadCargada = ciudadRepository.findById(hospedajeDTO.getId_ciudad())
                                .orElseThrow(() -> new RuntimeException(
                                                "Ciudad no encontrada: " + hospedajeDTO.getId_ciudad()));

                TipoHospedaje tipoHospedajeCargado = tipoHospedajeRepository
                                .findById(hospedajeDTO.getId_tipo_hospedaje())
                                .orElseThrow(() -> new RuntimeException(
                                                "Tipo de hospedaje no encontrado: "
                                                                + hospedajeDTO.getId_tipo_hospedaje()));

                hospedaje.setDescripcion(hospedajeDTO.getDescripcion());
                hospedaje.setImagen(hospedajeDTO.getImagen());
                hospedaje.setPrecio_por_noche(hospedajeDTO.getPrecio_por_noche());
                hospedaje.setFecha_modificacion(LocalDate.now());
                hospedaje.setCiudad(ciudadCargada);
                hospedaje.setId_tipo_hospedaje(tipoHospedajeCargado);
                hospedaje.setServicios(serviciosCargados);

                return hospedajeRepository.save(hospedaje);
        }

        @Override
        public Hospedaje eliminarHospedaje(Long id) {
                Hospedaje hospedaje = hospedajeRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Hospedaje no encontrado con el ID: " + id));

                hospedaje.setBorrado(true);
                return hospedajeRepository.save(hospedaje);
        }

        public List<Hospedaje> findAll() {
                return hospedajeRepository.findAllByBorradoFalse();
        }

        public List<Hospedaje> findFiltered(String pais, String ciudad, String tipo, Set<Long> servicioIds) {
                // Obtener todos los hospedajes con los filtros base
                List<Hospedaje> hospedajesBase = hospedajeRepository.findByBaseFilters(pais, ciudad, tipo);

                // Si no hay filtro de servicios, devolver todos los hospedajes base
                if (servicioIds == null || servicioIds.isEmpty()) {
                        return hospedajesBase;
                }

                // Filtrar por servicios
                return hospedajesBase.stream()
                                .filter(hospedaje -> servicioIds.isEmpty() ||
                                                hospedaje.getServicios().stream()
                                                                .map(Servicio::getId)
                                                                .collect(Collectors.toSet())
                                                                .containsAll(servicioIds))
                                .collect(Collectors.toList());
        }

}
