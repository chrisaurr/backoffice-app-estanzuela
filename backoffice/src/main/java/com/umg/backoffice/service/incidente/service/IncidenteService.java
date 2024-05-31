package com.umg.backoffice.service.incidente.service;

import com.umg.backoffice.modelo.entity.*;
import com.umg.backoffice.repository.IncidenteRepository;
import com.umg.backoffice.service.asignacion_incidente.service.ServiceForAsignacionIncidente;
import com.umg.backoffice.service.incidente.interfaces.InterfaceForIncidenteService;
import com.umg.backoffice.service.usuario.service.ServiceForUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class IncidenteService implements InterfaceForIncidenteService {

    @Autowired
    private IncidenteRepository incidenteRepository;
    
    @Autowired
    private ServiceForUsuario serviceForUsuario;

    @Autowired
    private ServiceForAsignacionIncidente serviceForAsignacionIncidente;

    @Transactional(readOnly=true)
    @Override
    public Page<Incidente> getAllIncidentes(Integer estado, Pageable pageable) {
        return incidenteRepository.findByEstadoNotOrderByIdDesc(estado, pageable);
    }

    @Transactional
    @Override
    public Boolean updateEstadoIncidente(Long id, Integer estado) {
        Incidente incidente = incidenteRepository.findByIdAndEstadoNot(id, Constants.ESTADO_ELIMINADO);
        if(incidente == null)return false;
        incidente.setEstado(estado);
        Incidente incidenteActualizado = incidenteRepository.save(incidente);
        if(incidenteActualizado == null)return false;
        else return true;
    }

    @Transactional
    @Override
    public Boolean updateCantidadAtendidos(Long id, Integer cantidadAtendidos) {
        Incidente incidente = incidenteRepository.findByIdAndEstadoNot(id, Constants.ESTADO_ELIMINADO);
        if(incidente == null)return false;
        incidente.setCantidadAtendidos(cantidadAtendidos);
        Incidente incidenteActualizado = incidenteRepository.save(incidente);
        if(incidenteActualizado == null)return false;
        else return true;
    }

    @Transactional(readOnly=true)
    @Override
    public List<Incidente> getIncidentesByDescripcion(String descripcion, Integer estado) {
        return incidenteRepository.findByDescripcionContainingIgnoreCaseAndEstadoNot(descripcion, estado);
    }

    @Transactional(readOnly=true)
    @Override
    public List<Incidente> getIncidentesByDireccion(String direccion, Integer estado) {
        return incidenteRepository.findByDireccionContainingIgnoreCaseAndEstadoNot(direccion, estado);
    }

    @Transactional(readOnly=true)
    @Override
    public List<Incidente> getIncidentesByReferenciaDeDireccion(String referenciaDeDireccion, Integer estado) {
        return incidenteRepository.findByReferenciaDeDireccionContainingIgnoreCaseAndEstadoNot(referenciaDeDireccion, estado);
    }

    @Transactional(readOnly=true)
    @Override
    public List<Incidente> getIncidentesByFechaBetween(Instant fechaInicio, Instant fechaFin, Integer estado) {
        return incidenteRepository.findByFechaBetweenAndEstadoNot(fechaInicio, fechaFin, estado);
    }

    @Transactional(readOnly=true)
    @Override
    public List<Incidente> getIncidentesByEstado(Integer estado) {
        return incidenteRepository.findByEstado(estado);
    }

    @Transactional(readOnly=true)
    @Override
    public List<Incidente> getIncidentesByCategoria(CategoriaIncidente categoriaIncidente, Integer estado) {
        return incidenteRepository.findByIdCategoriaAndEstadoNot(categoriaIncidente, estado);
    }

    @Transactional(readOnly=true)
    @Override
    public List<Incidente> getIncidentesByCiudadano(Ciudadano ciudadano, Integer estado) {
        return incidenteRepository.findByIdCiudadanoAndEstadoNot(ciudadano, estado);
    }

    @Transactional(readOnly=true)
    @Override
    public Incidente getIncidenteById(Long id, Integer estado) {
        return incidenteRepository.findByIdAndEstadoNot(id, estado);
    }

    @Transactional(readOnly=true)
    @Override
    public Page<Incidente> busquedaCompuesta(String descripcion, String direccion, Integer estado, CategoriaIncidente categoriaIncidente, Instant fechaInicio, Instant fechaFin, Pageable pageable) {
        return incidenteRepository.busquedaCompuesta(descripcion, direccion, estado, categoriaIncidente, fechaInicio, fechaFin, pageable);
    }

    @Transactional(readOnly=true)
    @Override
    public Page<AsignacionIncidente> getAsignacionesByUserName(String userName, Pageable pageable) {

        Usuario usuario = serviceForUsuario.findByUserName(userName, Constants.ESTADO_ELIMINADO).orElse(null);

        if(usuario == null)return null;

        return serviceForAsignacionIncidente.findAll(usuario.getId(), Constants.ESTADO_ACTIVO, pageable);
    }


}
