package com.umg.backoffice.repository;

import com.umg.backoffice.modelo.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    Set<Usuario> findByNombreContainingIgnoreCaseAndEstadoNot(String nombre, Integer estado);
    Set<Usuario> findByApellidosContainingIgnoreCaseAndEstadoNot(String apellido, Integer estado);
    Set<Usuario> findByEstadoNot(Integer estado);
    Usuario findByIdAndEstadoNot(Long id, Integer estado);
}
