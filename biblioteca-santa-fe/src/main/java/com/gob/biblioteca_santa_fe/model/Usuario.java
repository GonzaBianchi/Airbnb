package com.gob.biblioteca_santa_fe.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import java.util.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.time.Instant;
import java.time.LocalDate;

@Data
@Builder
@Entity
@Table(name = "usuario")
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

    @Column(unique = true, nullable = false)
    private String dni;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(name = "fecha_creacion")
    private Instant fecha_creacion;

    @Column(name = "fecha_modificacion")
    private Instant fecha_modificacion;

    @Column(name = "fecha_nacimiento")
    private LocalDate fecha_nacimiento;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_tipo_usuario", joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "id_tipo_usuario"))
    private Set<TipoUsuario> tipoUsuarios = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (tipoUsuarios == null || tipoUsuarios.isEmpty()) {
            return new ArrayList<>();
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (TipoUsuario tipoUsuario : tipoUsuarios) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + tipoUsuario.getNombre()));
        }
        System.out.println("authorities: " + authorities);
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