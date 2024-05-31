package com.umg.backoffice.service.usuario.service;

import com.umg.backoffice.modelo.entity.Constants;
import com.umg.backoffice.modelo.entity.Usuario;
import com.umg.backoffice.repository.UsuarioRepository;
import com.umg.backoffice.service.usuario.interfaces.InterfaceForUsuario;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class ServiceForUsuario implements InterfaceForUsuario {

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        Usuario findUserIfExist = usuarioRepository.findByUsernameAndEstadoNot(usuario.getUsername(), Constants.ESTADO_ELIMINADO).orElse(null);
        //validando que el username sea unico
        if (findUserIfExist != null) {
            return null;
        }

        String password = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(password);
        return usuarioRepository.save(usuario);
    }

    @Transactional
    @Override
    public Boolean deleteById(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null)return false;

        UUID uuid = UUID.randomUUID();
        String stringLimpio = uuid.toString().replaceAll("-", "");
        String random = stringLimpio.substring(0, 7);

        usuario.setUsername(usuario.getUsername()+stringLimpio);
        usuario.setEstado(Constants.ESTADO_ELIMINADO);
        Usuario deleteUser = usuarioRepository.save(usuario);

        return deleteUser != null;
    }

    @Override
    public Optional<Usuario> findByUserName(String username, Integer estado) {
        return usuarioRepository.findByUsernameAndEstadoNot(username, estado);
    }

}
