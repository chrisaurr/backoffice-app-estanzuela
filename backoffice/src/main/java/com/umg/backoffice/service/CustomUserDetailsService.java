package com.umg.backoffice.service;

import com.umg.backoffice.modelo.entity.Constants;
import com.umg.backoffice.modelo.entity.Usuario;
import com.umg.backoffice.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsernameAndEstadoNot(username, Constants.ESTADO_ELIMINADO).orElse(null);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado " + username);
        }

        return User.withUsername(usuario.getUsername())
                .password(usuario.getPassword())
                .roles("ADMIN")
                .build();
    }
}
