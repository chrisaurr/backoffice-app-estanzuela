package com.umg.backoffice.service.asignacion_incidente.interfaces;

import com.umg.backoffice.modelo.entity.AsignacionIncidente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface InterfaceForAsignacionIncidente {

    Page<AsignacionIncidente> findAll(Long userId, Integer estado, Pageable pageable);
    AsignacionIncidente findOneAsignacion(Long id);
    AsignacionIncidente save(AsignacionIncidente asignacionIncidente);
    AsignacionIncidente cambiarAestadoTerminado(Long id, Integer estado);

    Set<AsignacionIncidente> findAllAsignacionIncidentesByIdIncidente(Long idIncidente);

    Object eliminarAsignacionesIncidente(Long idIncidente);

}
