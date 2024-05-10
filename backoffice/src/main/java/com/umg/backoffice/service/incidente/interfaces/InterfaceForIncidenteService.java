package com.umg.backoffice.service.incidente.interfaces;

import com.umg.backoffice.modelo.entity.CategoriaIncidente;
import com.umg.backoffice.modelo.entity.Ciudadano;
import com.umg.backoffice.modelo.entity.Incidente;

import java.time.Instant;
import java.util.List;

public interface InterfaceForIncidenteService {
    List<Incidente> getAllIncidentes(Integer estado);

    Boolean updateEstadoIncidente(Long id, Integer estado);

    Boolean updateCantidadAtendidos(Long id, Integer cantidadAtendidos);

    List<Incidente> getIncidentesByDescripcion(String descripcion, Integer estado);

    List<Incidente> getIncidentesByDireccion(String direccion, Integer estado);

    List<Incidente> getIncidentesByReferenciaDeDireccion(String referenciaDeDireccion, Integer estado);

    List<Incidente> getIncidentesByFechaBetween(Instant fechaInicio, Instant fechaFin, Integer estado);

    List<Incidente> getIncidentesByEstado(Integer estado);

    List<Incidente> getIncidentesByCategoria(CategoriaIncidente categoriaIncidente, Integer estado);

    List<Incidente> getIncidentesByCiudadano(Ciudadano ciudadano, Integer estado);

    Incidente getIncidenteById(Long id, Integer estado);

    List<Incidente> busquedaCompuesta(String descripcion, String direccion, Integer estado, CategoriaIncidente categoriaIncidente, Instant fechaInicio, Instant fechaFin);
}
