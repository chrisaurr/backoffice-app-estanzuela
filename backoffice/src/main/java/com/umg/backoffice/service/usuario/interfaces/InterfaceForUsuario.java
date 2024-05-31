package com.umg.backoffice.service.usuario.interfaces;

import com.umg.backoffice.modelo.entity.Usuario;

import java.util.Optional;
import java.util.Set;

public interface InterfaceForUsuario {

    Set<Usuario> findByNameLike(String nombre);
    Set<Usuario> findByApellidoLike(String apellido);
    Set<Usuario> findAll();
    Usuario findById(Long id);

    Usuario save(Usuario usuario);
    Boolean deleteById(Long id);

    Optional<Usuario> findByUserName(String username, Integer estado);
}
