package com.gob.biblioteca_santa_fe.services;


import com.gob.biblioteca_santa_fe.DTOs.UsuarioDTO;
import com.gob.biblioteca_santa_fe.model.Usuario;
import com.gob.biblioteca_santa_fe.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {


    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Usuario registrOneCustomer(UsuarioDTO usuarioDTO) {

        validatePassword(usuarioDTO);


        Usuario usuario = new Usuario();
        usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword())); // Encriptar la contraseña
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setEmail(usuarioDTO.getEmail());


        return usuarioRepository.save(usuario);
    }
    //valida contraseña
    private void validatePassword(UsuarioDTO usuarioDTO) {
        if (usuarioDTO.getPassword().length() < 8) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres.");
        }

    }




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
