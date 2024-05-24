package com.umg.backoffice.repository;

import com.umg.backoffice.modelo.entity.AsignacionIncidente;
import com.umg.backoffice.modelo.entity.Incidente;
import com.umg.backoffice.modelo.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AsignacionIncidenteRepository extends JpaRepository<AsignacionIncidente,Long> {

    Set<AsignacionIncidente> findByIdUsuarioAndEstadoOrderByIdDesc(Usuario idUsuario, Integer estado);

    Set<AsignacionIncidente> findByIdIncidenteOrderByIdDesc(Incidente incidente);

}
