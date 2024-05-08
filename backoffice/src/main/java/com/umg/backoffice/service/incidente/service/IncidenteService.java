package com.umg.backoffice.service.incidente.service;

import com.umg.backoffice.modelo.entity.CategoriaIncidente;
import com.umg.backoffice.modelo.entity.Ciudadano;
import com.umg.backoffice.modelo.entity.Incidente;
import com.umg.backoffice.repository.IncidenteRepository;
import com.umg.backoffice.service.incidente.interfaces.InterfaceForIncidenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class IncidenteService implements InterfaceForIncidenteService {

    @Autowired
    private IncidenteRepository incidenteRepository;

    @Transactional(readOnly=true)
    @Override
    public List<Incidente> getAllIncidentes() {
        return incidenteRepository.findAllByOrderByIdDesc();
    }

    @Transactional
    @Override
    public Boolean updateEstadoIncidente(Long id, Integer estado) {
        Incidente incidente = incidenteRepository.findById(id).orElse(null);
        if(incidente == null)return false;
        incidente.setEstado(estado);
        Incidente incidenteActualizado = incidenteRepository.save(incidente);
        if(incidenteActualizado == null)return false;
        else return true;
    }

    @Transactional
    @Override
    public Boolean updateCantidadAtendidos(Long id, Integer cantidadAtendidos) {
        Incidente incidente = incidenteRepository.findById(id).orElse(null);
        if(incidente == null)return false;
        incidente.setCantidadAtendidos(cantidadAtendidos);
        Incidente incidenteActualizado = incidenteRepository.save(incidente);
        if(incidenteActualizado == null)return false;
        else return true;
    }

    @Override
    public List<Incidente> getIncidentesByDescripcion(String descripcion) {
        return List.of();
    }

    @Override
    public List<Incidente> getIncidentesByDireccion(String direccion) {
        return List.of();
    }

    @Override
    public List<Incidente> getIncidentesByReferenciaDeDireccion(String referenciaDeDireccion) {
        return List.of();
    }

    @Override
    public List<Incidente> getIncidentesByFechaBetween(Instant fechaInicio, Instant fechaFin) {
        return List.of();
    }

    @Override
    public List<Incidente> getIncidentesByEstado(Integer estado) {
        return List.of();
    }

    @Override
    public List<Incidente> getIncidentesByCategoria(CategoriaIncidente categoriaIncidente) {
        return List.of();
    }

    @Override
    public List<Incidente> getIncidentesByCiudadano(Ciudadano ciudadano) {
        return List.of();
    }

}
