package com.gob.biblioteca_santa_fe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import java.util.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
@Data
@Entity
@Table(name = "airbnb.usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(unique = true, nullable = false)
    private String dni;

    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario", nullable = false)
    private TipoUsuario tipoUsuario;

    @ManyToMany
    @JoinTable(
            name = "usuario_tipo_usuario",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_tipo_usuario")
    )
    private Set<TipoUsuario> tipoUsuarios = new HashSet<>();
    public enum TipoUsuario {
        INQUILINO, ANFITRION, ADMINISTRADOR
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (tipoUsuarios == null || tipoUsuarios.isEmpty()) {
            return new ArrayList<>(); // Retorna una lista vacía si no hay roles
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        // Recorre la lista de tipoUsuarios y agrega cada rol con el prefijo "ROLE"
        for (TipoUsuario tipoUsuario : tipoUsuarios) {
           //ayudaaaaaa authorities.add(new SimpleGrantedAuthority("ROLE" + tipoUsuario.gettipoUsuario()));
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}