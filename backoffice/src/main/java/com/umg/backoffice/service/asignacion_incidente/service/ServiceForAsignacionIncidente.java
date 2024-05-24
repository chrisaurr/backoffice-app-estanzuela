package com.umg.backoffice.service.asignacion_incidente.service;

import com.umg.backoffice.modelo.entity.AsignacionIncidente;
import com.umg.backoffice.modelo.entity.Incidente;
import com.umg.backoffice.modelo.entity.Usuario;
import com.umg.backoffice.repository.AsignacionIncidenteRepository;
import com.umg.backoffice.service.asignacion_incidente.interfaces.InterfaceForAsignacionIncidente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class ServiceForAsignacionIncidente implements InterfaceForAsignacionIncidente {

    @Autowired
    AsignacionIncidenteRepository asignacionIncidenteRepository;

    @Transactional(readOnly = true)
    @Override
    public Set<AsignacionIncidente> findAll(Long userId, Integer estado) {
        Usuario usuario = new Usuario();
        usuario.setId(userId);
        return asignacionIncidenteRepository.findByIdUsuarioAndEstadoOrderByIdDesc(usuario, estado);
    }

    @Transactional(readOnly = true)
    @Override
    public AsignacionIncidente findOneAsignacion(Long id) {
        return asignacionIncidenteRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public AsignacionIncidente save(AsignacionIncidente asignacionIncidente) {
        return asignacionIncidenteRepository.save(asignacionIncidente);
    }

    @Transactional
    @Override
    public AsignacionIncidente cambiarAestadoTerminado(Long id, Integer estado) {
        AsignacionIncidente asignacionIncidente = findOneAsignacion(id);
        if(asignacionIncidente != null){
            asignacionIncidente.setEstado(estado);
            return asignacionIncidenteRepository.save(asignacionIncidente);
        }
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public Set<AsignacionIncidente> findAllAsignacionIncidentesByIdIncidente(Long idIncidente) {
        Incidente incidete = new Incidente();
        incidete.setId(idIncidente);

        return asignacionIncidenteRepository.findByIdIncidenteOrderByIdDesc(incidete);
    }

    @Override
    public Object eliminarAsignacionesIncidente(Long idIncidente) {
        return asignacionIncidenteRepository.updateEstadoAsignacionesIncidentesToZero(idIncidente);
    }
}
