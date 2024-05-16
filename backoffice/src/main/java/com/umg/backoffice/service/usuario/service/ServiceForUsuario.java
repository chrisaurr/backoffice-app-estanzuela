package com.umg.backoffice.service.usuario.service;

import com.umg.backoffice.modelo.entity.Constants;
import com.umg.backoffice.modelo.entity.Usuario;
import com.umg.backoffice.repository.UsuarioRepository;
import com.umg.backoffice.service.usuario.interfaces.InterfaceForUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class ServiceForUsuario implements InterfaceForUsuario {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly=true)
    @Override
    public Set<Usuario> findByNameLike(String nombre) {
        return usuarioRepository.findByNombreContainingIgnoreCaseAndEstadoNot(nombre, Constants.ESTADO_ELIMINADO);
    }

    @Transactional(readOnly=true)
    @Override
    public Set<Usuario> findByApellidoLike(String apellido) {
        return usuarioRepository.findByApellidosContainingIgnoreCaseAndEstadoNot(apellido, Constants.ESTADO_ELIMINADO);
    }

    @Transactional(readOnly=true)
    @Override
    public Set<Usuario> findAll() {
        return usuarioRepository.findByEstadoNot(Constants.ESTADO_ELIMINADO);
    }

    @Transactional(readOnly=true)
    @Override
    public Usuario findById(Long id) {
        return usuarioRepository.findByIdAndEstadoNot(id, Constants.ESTADO_ELIMINADO);
    }

    @Transactional
    @Override
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Transactional
    @Override
    public Boolean deleteById(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null)return false;

        usuario.setEstado(Constants.ESTADO_ELIMINADO);
        Usuario deleteUser = usuarioRepository.save(usuario);

        return deleteUser != null;
    }
}
