package com.umg.backoffice.service.asignacion_incidente.interfaces;

import com.umg.backoffice.modelo.entity.AsignacionIncidente;

import java.util.Set;

public interface InterfaceForAsignacionIncidente {

    Set<AsignacionIncidente> findAll(Long userId, Integer estado);
    AsignacionIncidente findOneAsignacion(Long id);
    AsignacionIncidente save(AsignacionIncidente asignacionIncidente);
    AsignacionIncidente cambiarAestadoTerminado(Long id, Integer estado);

}
